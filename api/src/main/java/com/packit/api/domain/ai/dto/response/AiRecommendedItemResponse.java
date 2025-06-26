package com.packit.api.domain.ai.dto.response;

import com.packit.api.domain.ai.client.dto.AiRecommendApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "AI 추천 아이템 응답 DTO")
public record AiRecommendedItemResponse(
        @Schema(description = "추천된 항목 이름") String name,
        @Schema(description = "수량") int quantity
) {    @Builder
    public static AiRecommendedItemResponse from(AiRecommendApiResponse apiResponse) {
    return new AiRecommendedItemResponse(apiResponse.getName(), apiResponse.getQuantity());
    }
}
