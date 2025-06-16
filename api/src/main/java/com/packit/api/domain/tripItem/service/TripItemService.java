package com.packit.api.domain.tripItem.service;

import com.packit.api.domain.templateItem.entity.TemplateItem;
import com.packit.api.domain.templateItem.repository.TemplateItemRepository;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.repository.TripCategoryRepository;
import com.packit.api.domain.tripCategory.service.TripCategoryService;
import com.packit.api.domain.tripItem.dto.request.TripItemCreateRequest;
import com.packit.api.domain.tripItem.dto.request.TripItemListCreateRequest;
import com.packit.api.domain.tripItem.dto.response.TripItemResponse;
import com.packit.api.domain.tripItem.entity.TripItem;
import com.packit.api.domain.tripItem.repository.TripItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.packit.api.domain.tripCategory.entity.TripCategoryStatus.*;

@Service
@RequiredArgsConstructor
public class TripItemService {

    private final TripItemRepository tripItemRepository;
    private final TripCategoryRepository tripCategoryRepository;
    private final TemplateItemRepository templateItemRepository;
    private final TripCategoryService tripCategoryService;

    public TripItemResponse create(Long tripCategoryId, TripItemCreateRequest request, Long userId) {
        TripCategory category = getCategoryOwnedByUser(tripCategoryId, userId);
        TripItem item = TripItem.of(category, request.name(), request.quantity());
        item.update(request.name(), request.quantity(), request.memo());
        tripItemRepository.save(item);
        updateCategoryStatusAfterItemChange(item.getTripCategory());
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
        updateCategoryStatusAfterItemChange(item.getTripCategory());
    }

    public void delete(Long itemId, Long userId) {
        TripItem item = getItemOwnedByUser(itemId, userId);
        tripItemRepository.delete(item);
        updateCategoryStatusAfterItemChange(item.getTripCategory());
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

    public void addItemsFromTemplate(Long tripCategoryId, List<Long> templateItemIds, Long userId) {
        TripCategory category = getCategoryOwnedByUser(tripCategoryId, userId);

        List<TemplateItem> templateItems = templateItemRepository.findAllById(templateItemIds);
        List<TripItem> tripItems = templateItems.stream()
                .map(template -> TripItem.builder()
                        .tripCategory(category)
                        .name(template.getName())
                        .quantity(template.getDefaultQuantity())
                        .isChecked(false)
                        .isSaved(true)
                        .isAiGenerated(false)
                        .memo(null)
                        .build())
                .toList();

        tripItemRepository.saveAll(tripItems);
    }

    private void updateCategoryStatusAfterItemChange(TripCategory category) {
        List<TripItem> items = tripItemRepository.findAllByTripCategory(category);

        if (items.isEmpty()) {
            category.updateStatus(NOT_STARTED);
            return;
        }

        long checkedCount = items.stream().filter(TripItem::isChecked).count();

        if (checkedCount == 0) {
            category.updateStatus(NOT_STARTED);
        } else if (checkedCount == items.size()) {
            category.updateStatus(COMPLETED);
        } else {
            category.updateStatus(IN_PROGRESS);
        }
    }

    @Transactional
    public void createBulkItems(Long tripId, Long tripCategoryId, TripItemListCreateRequest request) {
        TripCategory tripCategory = tripCategoryRepository.findByIdAndTripId(tripCategoryId, tripId)
                .orElseThrow(() -> new IllegalArgumentException("해당 TripCategory가 존재하지 않습니다."));

        List<TripItem> tripItems = request.getItems().stream()
                .map(item -> TripItem.of(
                        tripCategory,
                        item.name(),
                        item.quantity() != null ? item.quantity() : 1,
                        item.memo() // memo를 함께 받는 새로운 of() 정적 팩토리 추가
                ))
                .toList();

        tripItemRepository.saveAll(tripItems);
    }
}
