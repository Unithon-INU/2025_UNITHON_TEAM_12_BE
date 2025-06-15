package com.packit.api.domain.tripCategory.dto.request;

import com.packit.api.domain.tripCategory.entity.TripCategoryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TripCategoryStatusUpdateRequest {
    @NotNull
    private TripCategoryStatus status;
}
