package com.packit.api.domain.trip.service;

import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.trip.dto.request.TripCreateRequest;
import com.packit.api.domain.trip.dto.request.TripUpdateRequest;
import com.packit.api.domain.trip.dto.response.TripResponse;
import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.trip.repository.TripRepository;
import com.packit.api.domain.user.entity.User;
import com.packit.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public TripResponse createTrip(TripCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Trip trip = Trip.of(user, request);
        tripRepository.save(trip);

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
}
