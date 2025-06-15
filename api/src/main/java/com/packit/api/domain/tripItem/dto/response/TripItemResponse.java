package com.packit.api.domain.tripItem.dto.response;

import com.packit.api.domain.tripItem.entity.TripItem;

public record TripItemResponse(
        Long id,
        String name,
        Integer quantity,
        boolean isChecked,
        boolean isSaved,
        boolean isAiGenerated,
        String memo
) {
    public static TripItemResponse from(TripItem item) {
        return new TripItemResponse(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.isChecked(),
                item.isSaved(),
                item.isAiGenerated(),
                item.getMemo()
        );
    }
}
