package com.packit.api.domain.tripCategory.controller;

import com.packit.api.common.response.ListResponse;
import com.packit.api.common.response.SingleResponse;
import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.tripCategory.dto.request.TripCategoryCreateRequest;
import com.packit.api.domain.tripCategory.dto.request.TripCategoryStatusUpdateRequest;
import com.packit.api.domain.tripCategory.dto.response.TripCategoryResponse;
import com.packit.api.domain.tripCategory.dto.response.TripCategoryProgressResponse;
import com.packit.api.domain.tripCategory.entity.TripCategoryStatus;
import com.packit.api.domain.tripCategory.service.TripCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "TripCategory", description = "여행 카테고리 관리 API")
public class TripCategoryController {

    private final TripCategoryService tripCategoryService;

    @Operation(summary = "여행에 카테고리 추가", description = "여행(tripId)에 새로운 카테고리를 추가합니다. 기본 제공 카테고리 또는 사용자 정의 이름을 기반으로 생성할 수 있습니다.")
    @PostMapping("/trips/{tripId}/trip-categories")
    public ResponseEntity<SingleResponse<TripCategoryResponse>> createCategory(
            @PathVariable Long tripId,
            @RequestBody @Valid TripCategoryCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        TripCategoryResponse response = tripCategoryService.create(tripId, request, userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "카테고리 생성 완료", response));
    }

    @Operation(summary = "여행별 카테고리 목록 조회", description = "해당 tripId에 속한 모든 TripCategory를 조회합니다.")
    @GetMapping("/trips/{tripId}/trip-categories")
    public ResponseEntity<ListResponse<TripCategoryResponse>> getCategories(@PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        List<TripCategoryResponse> list = tripCategoryService.getTripCategories(tripId, userId);
        return ResponseEntity.ok(new ListResponse<>(200, "카테고리 목록 조회 완료", list));
    }

    @Operation(summary = "카테고리 상태 변경", description = "짐싸기 상태(NOT_STARTED, IN_PROGRESS, COMPLETED)를 변경합니다.")
    @PatchMapping("/trip-categories/{tripCategoryId}")
    public ResponseEntity<SingleResponse<String>> updateStatus(
            @PathVariable Long tripCategoryId,
            @RequestBody @Valid TripCategoryStatusUpdateRequest request
    ) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripCategoryService.updateStatus(tripCategoryId, request.getStatus(), userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "카테고리 상태 수정 완료", "success"));
    }

    @Operation(summary = "카테고리 삭제", description = "해당 TripCategory를 삭제합니다.")
    @DeleteMapping("/trip-categories/{tripCategoryId}")
    public ResponseEntity<SingleResponse<String>> deleteCategory(@PathVariable Long tripCategoryId) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripCategoryService.delete(tripCategoryId, userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "카테고리 삭제 완료", "success"));
    }

    @Operation(summary = "카테고리별 진행률", description = "여행에 포함된 각 카테고리의 진행률(전체 항목 수, 완료 수 등)을 반환합니다.")
    @GetMapping("/trips/{tripId}/trip-categories/progress")
    public ResponseEntity<ListResponse<TripCategoryProgressResponse>> getCategoryProgress(@PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        List<TripCategoryProgressResponse> progressList = tripCategoryService.getCategoryProgress(tripId, userId);
        return ResponseEntity.ok(new ListResponse<>(200, "카테고리 진행률 조회 완료", progressList));
    }
}
