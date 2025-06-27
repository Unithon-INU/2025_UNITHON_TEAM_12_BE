package com.packit.api.domain.ai.controller;

import com.packit.api.common.response.ListResponse;
import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.ai.dto.request.AiRecommendRequest;
import com.packit.api.domain.ai.dto.response.AiRecommendedCategoryResponse;
import com.packit.api.domain.ai.dto.response.AiRecommendedItemResponse;
import com.packit.api.domain.ai.service.AiRecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
@Tag(name = "AI 추천", description = "AI 기반 추천 아이템 API")
public class AiRecommendController {

    private final AiRecommendService aiRecommendService;

    @PostMapping("/recommend")
    @Operation(summary = "AI 추천 요청", description = "TripCategory에 기반한 추천 아이템을 반환합니다.")
    public ResponseEntity<ListResponse<AiRecommendedCategoryResponse>> recommend(
            @RequestBody @Valid AiRecommendRequest request
    ) {
        Long userId = SecurityUtils.getCurrentUserId();
        List<AiRecommendedCategoryResponse> items = aiRecommendService.recommendItems(userId, request);
        return ResponseEntity.ok(new ListResponse<>(200, "AI 추천 생성 완료", items));
    }

    @GetMapping("/recommend/items")
    @Operation(summary = "카테고리별 AI 추천 아이템 조회", description = "TripCategory ID를 기준으로 AI 추천 아이템을 조회합니다.")
    public ResponseEntity<ListResponse<AiRecommendedItemResponse>> getRecommendedItemsByCategory(
            @RequestParam Long tripCategoryId
    ) {
        List<AiRecommendedItemResponse> response = aiRecommendService.getRecommendationsByTripCategory(tripCategoryId);
        return ResponseEntity.ok(new ListResponse<>(200, "AI 추천 리스트 조회",response));
    }
}
