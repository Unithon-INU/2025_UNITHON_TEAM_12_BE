package com.packit.api.domain.tripCategory.service;

import com.packit.api.domain.category.entity.Category;
import com.packit.api.domain.category.repository.CategoryRepository;
import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.trip.repository.TripRepository;
import com.packit.api.domain.tripCategory.dto.request.TripCategoryCreateRequest;
import com.packit.api.domain.tripCategory.dto.response.TripCategoryResponse;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.entity.TripCategoryStatus;
import com.packit.api.domain.tripCategory.repository.TripCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripCategoryService {

    private final TripRepository tripRepository;
    private final CategoryRepository categoryRepository;
    private final TripCategoryRepository tripCategoryRepository;

    public TripCategoryResponse create(Long tripId, TripCategoryCreateRequest request, Long userId) {
        Trip trip = getTripOwnedByUser(tripId, userId);

        Category category = null;
        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));
        }

        TripCategory tripCategory = TripCategory.of(trip, category, request.name());
        tripCategoryRepository.save(tripCategory);

        return TripCategoryResponse.from(tripCategory);
    }

    public List<TripCategoryResponse> getTripCategories(Long tripId, Long userId) {
        getTripOwnedByUser(tripId, userId); // 권한 검증
        return tripCategoryRepository.findAllByTripId(tripId).stream()
                .map(TripCategoryResponse::from)
                .toList();
    }

    public void updateStatus(Long tripCategoryId, TripCategoryStatus status, Long userId) {
        TripCategory tripCategory = tripCategoryRepository.findById(tripCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        validateTripOwner(tripCategory.getTrip(), userId);
        tripCategory.updateStatus(status);
    }

    public void delete(Long tripCategoryId, Long userId) {
        TripCategory tripCategory = tripCategoryRepository.findById(tripCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        validateTripOwner(tripCategory.getTrip(), userId);
        tripCategoryRepository.delete(tripCategory);
    }

    private Trip getTripOwnedByUser(Long tripId, Long userId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("여행을 찾을 수 없습니다."));
        validateTripOwner(trip, userId);
        return trip;
    }

    private void validateTripOwner(Trip trip, Long userId) {
        if (!trip.getUser().getId().equals(userId)) {
            throw new SecurityException("본인의 여행만 접근할 수 있습니다.");
        }
    }
}