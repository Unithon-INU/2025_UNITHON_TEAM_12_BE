package com.packit.api.domain.tripItem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record TripItemCreateRequest(
        @NotBlank
        @Schema(description = "아이템 이름", example = "양말")
        String name,

        @Schema(description = "수량", example = "3")
        Integer quantity,

        @Schema(description = "메모", example = "겨울용")
        String memo
) {}
