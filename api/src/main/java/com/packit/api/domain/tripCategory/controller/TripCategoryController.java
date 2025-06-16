package com.packit.api.domain.tripCategory.controller;

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
    public ResponseEntity<TripCategoryResponse> createCategory(
            @PathVariable Long tripId,
            @RequestBody @Valid TripCategoryCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripCategoryService.create(tripId, request, userId));
    }

    @Operation(summary = "여행별 카테고리 목록 조회", description = "해당 tripId에 속한 모든 TripCategory를 조회합니다.")
    @GetMapping("/trips/{tripId}/trip-categories")
    public ResponseEntity<List<TripCategoryResponse>> getCategories(@PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripCategoryService.getTripCategories(tripId, userId));
    }

    @Operation(summary = "카테고리 상태 변경", description = "짐싸기 상태(NOT_STARTED, IN_PROGRESS, COMPLETED)를 변경합니다.")
    @PatchMapping("/trip-categories/{tripCategoryId}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long tripCategoryId,
            @RequestBody @Valid TripCategoryStatusUpdateRequest request
    ) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripCategoryService.updateStatus(tripCategoryId, request.getStatus(), userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "카테고리 삭제", description = "해당 TripCategory를 삭제합니다.")
    @DeleteMapping("/trip-categories/{tripCategoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long tripCategoryId) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripCategoryService.delete(tripCategoryId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "카테고리별 진행률", description = "여행에 포함된 각 카테고리의 진행률(전체 항목 수, 완료 수 등)을 반환합니다.")
    @GetMapping("/trips/{tripId}/trip-categories/progress")
    public ResponseEntity<List<TripCategoryProgressResponse>> getCategoryProgress(@PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripCategoryService.getCategoryProgress(tripId, userId));
    }
}
