package com.packit.api.domain.tripCategory.dto.response;

import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.entity.TripCategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TripCategoryProgressResponse {
    private Long tripCategoryId;
    private String name;
    private TripCategoryStatus status;

    public static TripCategoryProgressResponse of(TripCategory category) {
        return new TripCategoryProgressResponse(category.getId(), category.getName(),category.getStatus());
    }
}

