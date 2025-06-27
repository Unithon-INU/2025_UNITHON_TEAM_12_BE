package com.packit.api.domain.ai.dto.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "AI 추천 응답: 카테고리별 아이템 목록")
public record AiRecommendedCategoryResponse(
        @Schema(description = "카테고리 이름", example = "의류") String category,
        @Schema(description = "해당 카테고리에 포함된 추천 아이템 목록") List<AiRecommendedItemResponse> items
) {
    @Builder
    public static AiRecommendedCategoryResponse from(String category, List<AiRecommendedItemResponse> items) {
        return new AiRecommendedCategoryResponse(category, items);
    }
}
