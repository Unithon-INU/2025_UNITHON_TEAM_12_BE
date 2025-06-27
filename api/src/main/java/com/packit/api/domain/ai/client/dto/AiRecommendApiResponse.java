package com.packit.api.domain.ai.client.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class AiRecommendApiResponse {

    @Schema(description = "카테고리명", example = "의류")
    private String category;

    @Schema(description = "추천 아이템 목록")
    private List<AiRecommendItem> items;

    @Getter
    public static class AiRecommendItem {

        @Schema(description = "아이템 이름", example = "속옷")
        private String name;

        @Schema(description = "아이템 수량", example = "3")
        private int quantity;
    }
}