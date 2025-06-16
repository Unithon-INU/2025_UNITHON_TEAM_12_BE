package com.packit.api.domain.tripCategory.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "여행 카테고리 생성 요청")
public record TripCategoryCreateRequest(

        @Schema(description = "기본 카테고리 ID (없을 경우 사용자 정의)", example = "1")
        Long categoryId,

        @NotBlank
        @Schema(description = "카테고리 이름", example = "전자기기")
        String name
) {}
