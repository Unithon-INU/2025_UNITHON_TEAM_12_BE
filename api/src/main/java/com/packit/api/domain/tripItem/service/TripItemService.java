package com.packit.api.domain.tripItem.service;

import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.repository.TripCategoryRepository;
import com.packit.api.domain.tripItem.dto.request.TripItemCreateRequest;
import com.packit.api.domain.tripItem.dto.response.TripItemResponse;
import com.packit.api.domain.tripItem.entity.TripItem;
import com.packit.api.domain.tripItem.repository.TripItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripItemService {

    private final TripItemRepository tripItemRepository;
    private final TripCategoryRepository tripCategoryRepository;

    public TripItemResponse create(Long tripCategoryId, TripItemCreateRequest request, Long userId) {
        TripCategory category = getCategoryOwnedByUser(tripCategoryId, userId);
        TripItem item = TripItem.of(category, request.name(), request.quantity());
        item.update(request.name(), request.quantity(), request.memo());
        tripItemRepository.save(item);
        return TripItemResponse.from(item);
    }

    public List<TripItemResponse> getAll(Long tripCategoryId, Long userId) {
        getCategoryOwnedByUser(tripCategoryId, userId);
        return tripItemRepository.findAllByTripCategoryId(tripCategoryId).stream()
                .map(TripItemResponse::from)
                .toList();
    }

    public TripItemResponse update(Long itemId, TripItemCreateRequest request, Long userId) {
        TripItem item = getItemOwnedByUser(itemId, userId);
        item.update(request.name(), request.quantity(), request.memo());
        return TripItemResponse.from(item);
    }

    public void toggleCheck(Long itemId, Long userId) {
        TripItem item = getItemOwnedByUser(itemId, userId);
        item.toggleCheck();
    }

    public void delete(Long itemId, Long userId) {
        TripItem item = getItemOwnedByUser(itemId, userId);
        tripItemRepository.delete(item);
    }

    private TripItem getItemOwnedByUser(Long itemId, Long userId) {
        TripItem item = tripItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));
        validateTripCategoryOwner(item.getTripCategory(), userId);
        return item;
    }

    private TripCategory getCategoryOwnedByUser(Long tripCategoryId, Long userId) {
        TripCategory category = tripCategoryRepository.findById(tripCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        validateTripCategoryOwner(category, userId);
        return category;
    }

    private void validateTripCategoryOwner(TripCategory category, Long userId) {
        if (!category.getTrip().getUser().getId().equals(userId)) {
            throw new SecurityException("본인의 여행 항목만 관리할 수 있습니다.");
        }
    }
}
