package com.packit.api.domain.tripCategory.dto.response;

import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.entity.TripCategoryStatus;

public record TripCategoryResponse(
        Long id,
        String name,
        TripCategoryStatus status,
        boolean isDefault
) {
    public static TripCategoryResponse from(TripCategory tripCategory) {
        return new TripCategoryResponse(
                tripCategory.getId(),
                tripCategory.getName(),
                tripCategory.getStatus(),
                tripCategory.getCategory() != null
        );
    }
}
