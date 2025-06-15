package com.packit.api.domain.tripItem.controller;

import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.tripItem.dto.request.TripItemCreateRequest;
import com.packit.api.domain.tripItem.dto.request.TripItemFromTemplateRequest;
import com.packit.api.domain.tripItem.dto.response.TripItemResponse;
import com.packit.api.domain.tripItem.service.TripItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/trip-categories")
@RequiredArgsConstructor
@Tag(name = "TripItem", description = "짐 아이템 관리 API")
public class TripItemController {

    private final TripItemService tripItemService;

    @Operation(summary = "아이템 생성", description = "카테고리 내에 새로운 짐 아이템을 추가합니다.")
    @PostMapping("/{categoryId}/items")
    public ResponseEntity<TripItemResponse> createItem(
            @PathVariable Long categoryId,
            @RequestBody @Valid TripItemCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripItemService.create(categoryId, request, userId));
    }

    @Operation(summary = "아이템 목록 조회", description = "해당 카테고리에 속한 모든 아이템을 조회합니다.")
    @GetMapping("/{categoryId}/items")
    public ResponseEntity<List<TripItemResponse>> getItems(@PathVariable Long categoryId) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripItemService.getAll(categoryId, userId));
    }

    @Operation(summary = "아이템 수정", description = "아이템 이름, 수량, 메모를 수정합니다.")
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<TripItemResponse> updateItem(
            @PathVariable Long itemId,
            @RequestBody @Valid TripItemCreateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripItemService.update(itemId, request, userId));
    }

    @Operation(summary = "짐싸기 체크 여부 토글", description = "isChecked 상태를 토글합니다.")
    @PatchMapping("/items/{itemId}/check")
    public ResponseEntity<Void> toggleCheck(@PathVariable Long itemId) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripItemService.toggleCheck(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "아이템 삭제", description = "짐 아이템을 삭제합니다.")
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripItemService.delete(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "템플릿 아이템 복사", description = "선택한 템플릿 아이템을 TripItem으로 복사합니다.")
    @PostMapping("/{categoryId}/items/from-template")
    public ResponseEntity<Void> addFromTemplate(
            @PathVariable Long categoryId,
            @RequestBody TripItemFromTemplateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripItemService.addItemsFromTemplate(categoryId, request.templateItemIds(), userId);
        return ResponseEntity.ok().build();
    }
}
