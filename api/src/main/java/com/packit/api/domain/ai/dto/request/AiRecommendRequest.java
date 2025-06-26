package com.packit.api.domain.ai.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "AI 추천 요청 DTO")
public record AiRecommendRequest(
        @Schema(description = "여행 ID", example = "1")
        @NotNull Long tripId,

        @Schema(description = "TripCategory ID", example = "5")
        @NotNull Long tripCategoryId
) {}
