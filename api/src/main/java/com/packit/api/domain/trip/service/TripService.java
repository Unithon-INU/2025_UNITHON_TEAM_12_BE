package com.packit.api.domain.trip.service;

import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.trip.dto.request.TripCreateRequest;
import com.packit.api.domain.trip.dto.request.TripUpdateRequest;
import com.packit.api.domain.trip.dto.response.*;
import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.trip.repository.TripRepository;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.repository.TripCategoryRepository;
import com.packit.api.domain.tripItem.entity.TripItem;
import com.packit.api.domain.tripItem.repository.TripItemRepository;
import com.packit.api.domain.user.entity.User;
import com.packit.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.packit.api.domain.tripCategory.entity.TripCategoryStatus.COMPLETED;

@Service
@RequiredArgsConstructor
public class TripService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final TripCategoryRepository tripCategoryRepository;
    private final TripItemRepository tripItemRepository;
    private final TripInitializerService tripInitializerService;

    @Transactional
    public TripResponse createTrip(TripCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Trip trip = Trip.of(user, request);
        Trip savedTrip = tripRepository.save(trip);

        tripInitializerService.initializeDefaultsFor(savedTrip);

        return TripResponse.from(trip);
    }

    public List<TripResponse> getTripList(Long userId) {
        return tripRepository.findAllByUserId(userId).stream()
                .map(TripResponse::from)
                .toList();
    }

    public TripResponse getTripDetail(Long tripId, Long userId) {
        Trip trip = validateTripOwner(tripId, userId);
        return TripResponse.from(trip);
    }

    public TripResponse updateTrip(Long tripId, Long userId, TripUpdateRequest request) {
        Trip trip = validateTripOwner(tripId, userId);
        trip.update(request);  // 아래에 정의된 update 메서드 참고
        return TripResponse.from(trip);
    }

    public void deleteTrip(Long tripId, Long userId) {
        Trip trip = validateTripOwner(tripId, userId);
        tripRepository.delete(trip);
    }

    private Trip validateTripOwner(Long tripId, Long userId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("해당 여행을 찾을 수 없습니다."));
        if (!trip.getUser().getId().equals(userId)) {
            throw new SecurityException("본인의 여행만 조회/수정/삭제할 수 있습니다.");
        }
        return trip;
    }

    public TripProgressResponse getTripProgress(Long tripId, Long userId) {
        Trip trip = validateTripOwner(tripId, userId);
        List<TripCategory> categories = tripCategoryRepository.findAllByTripId(tripId);

        long total = categories.size();
        long completed = categories.stream()
                .filter(c -> c.getStatus() == COMPLETED)
                .count();

        double progress = total == 0 ? 0 : ((double) completed / total) * 100;

        return TripProgressResponse.of(total, completed, progress);
    }

    public TripSummaryResponse getTripSummaryByUser(Long userId) {
        int total = tripRepository.countByUserId(userId);
        int planned = tripRepository.countByUserIdAndIsCompletedFalse(userId);
        int completed = tripRepository.countByUserIdAndIsCompletedTrue(userId);
        return TripSummaryResponse.of(total, planned, completed);
    }

    public TripProgressCountResponse getTripProgressCount(Long tripId) {
        List<TripItem> items = tripItemRepository.findAllByTripId(tripId);

        int total = items.size();
        int checked = (int) items.stream().filter(TripItem::isChecked).count();

        return TripProgressCountResponse.of(tripId, total, checked);
    }

    public TripNearestResponse getNearestCompletedTrip(Long userId) {
        LocalDate today = LocalDate.now();
        Trip trip = tripRepository.findNearestCompletedTripAfterToday(userId, today)
                .orElseThrow(() -> new RuntimeException("TRIP_NOT_FOUND"));

        List<TripItem> items = tripItemRepository.findAllByTripId(trip.getId());
        int total = items.size();
        int checked = (int) items.stream().filter(TripItem::isChecked).count();

        return TripNearestResponse.from(trip, total, checked);
    }
}
