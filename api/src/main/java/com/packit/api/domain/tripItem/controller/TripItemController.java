package com.packit.api.domain.tripItem.controller;

import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.tripItem.dto.request.TripItemCreateRequest;
import com.packit.api.domain.tripItem.dto.request.TripItemFromTemplateRequest;
import com.packit.api.domain.tripItem.dto.request.TripItemListCreateRequest;
import com.packit.api.domain.tripItem.dto.response.TripItemResponse;
import com.packit.api.domain.tripItem.service.TripItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "TripItem", description = "여행 짐 아이템 관리 API")
public class TripItemController {

    private final TripItemService tripItemService;

    @Operation(summary = "여행 카테고리 내 아이템 생성", description = "여행 카테고리(tripCategoryId) 내에 새로운 아이템을 추가합니다.")
    @PostMapping("/trip-categories/{tripCategoryId}/trip-items")
    public ResponseEntity<TripItemResponse> createItem(
            @PathVariable Long tripCategoryId,
            @RequestBody @Valid TripItemCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripItemService.create(tripCategoryId, request, userId));
    }

    @Operation(summary = "여행 카테고리 내 아이템 목록 조회", description = "tripCategoryId에 해당하는 모든 아이템을 조회합니다.")
    @GetMapping("/trip-categories/{tripCategoryId}/trip-items")
    public ResponseEntity<List<TripItemResponse>> getItems(@PathVariable Long tripCategoryId) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripItemService.getAll(tripCategoryId, userId));
    }

    @Operation(summary = "아이템 수정", description = "아이템 이름, 수량, 메모 등의 정보를 수정합니다.")
    @PatchMapping("/trip-items/{tripItemId}")
    public ResponseEntity<TripItemResponse> updateItem(
            @PathVariable Long tripItemId,
            @RequestBody @Valid TripItemCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripItemService.update(tripItemId, request, userId));
    }

    @Operation(summary = "짐싸기 체크 상태 토글", description = "아이템의 isChecked 상태를 true/false로 전환합니다.")
    @PatchMapping("/trip-items/{tripItemId}/check")
    public ResponseEntity<Void> toggleCheck(@PathVariable Long tripItemId) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripItemService.toggleCheck(tripItemId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "아이템 삭제", description = "해당 아이템을 삭제합니다.")
    @DeleteMapping("/trip-items/{tripItemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long tripItemId) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripItemService.delete(tripItemId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "템플릿 아이템 복사", description = "선택된 템플릿 아이템들을 현재 여행 카테고리 내 아이템으로 복사합니다.")
    @PostMapping("/trip-categories/{tripCategoryId}/trip-items/from-template")
    public ResponseEntity<Void> addFromTemplate(
            @PathVariable Long tripCategoryId,
            @RequestBody TripItemFromTemplateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripItemService.addItemsFromTemplate(tripCategoryId, request.templateItemIds(), userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "TripItem 생성",
            description = "기본템플릿이든 사용자 입력이든, name/quantity/memo만 포함된 단순 리스트를 기반으로 TripItem 생성")
    @PostMapping("/trips/{tripId}/categories/{tripCategoryId}/items")
    public ResponseEntity<Void> createTripItems(
            @Parameter(description = "여행 ID") @PathVariable Long tripId,
            @Parameter(description = "여행 카테고리 ID") @PathVariable Long tripCategoryId,
            @RequestBody TripItemListCreateRequest request
    ) {
        tripItemService.createBulkItems(tripId, tripCategoryId, request);
        return ResponseEntity.ok().build();
    }


}