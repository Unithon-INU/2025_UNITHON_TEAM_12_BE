package com.packit.api.domain.tripCategory.controller;

import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.tripCategory.dto.request.TripCategoryCreateRequest;
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
@RequestMapping("/api/trips")
@RequiredArgsConstructor
@Tag(name = "TripCategory", description = "여행 카테고리 관리 API")
public class TripCategoryController {

    private final TripCategoryService tripCategoryService;

    @Operation(summary = "여행에 카테고리 추가", description = "기본 카테고리 선택 또는 사용자 정의 카테고리를 여행에 추가합니다.")
    @PostMapping("/{tripId}/categories")
    public ResponseEntity<TripCategoryResponse> createCategory(
            @PathVariable Long tripId,
            @RequestBody @Valid TripCategoryCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripCategoryService.create(tripId, request, userId));
    }

    @Operation(summary = "여행 카테고리 목록 조회", description = "여행 ID에 해당하는 모든 카테고리를 조회합니다.")
    @GetMapping("/{tripId}/categories")
    public ResponseEntity<List<TripCategoryResponse>> getCategories(@PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripCategoryService.getTripCategories(tripId, userId));
    }

    @Operation(summary = "카테고리 상태 변경", description = "짐싸기 상태(NOT_STARTED, IN_PROGRESS, COMPLETED)를 변경합니다.")
    @PatchMapping("/categories/{id}")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam TripCategoryStatus status) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripCategoryService.updateStatus(id, status, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "카테고리 삭제", description = "여행 카테고리를 삭제합니다.")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripCategoryService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "카테고리별 진행률", description = "각 카테고리의 총 항목 수, 완료 수, 진행 상태를 반환합니다.")
    @GetMapping("/{tripId}/progress/categories")
    public ResponseEntity<List<TripCategoryProgressResponse>> getCategoryProgress(@PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripCategoryService.getCategoryProgress(tripId, userId));
    }
}
