package com.packit.api.domain.templateItem.controller;

import com.packit.api.domain.templateItem.dto.TemplateItemResponse;
import com.packit.api.domain.templateItem.service.TemplateItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TemplateItem", description = "카테고리별 템플릿 아이템 조회 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TemplateItemController {

    private final TemplateItemService templateItemService;


    @Operation(
            summary = "카테고리별 템플릿 아이템 조회",
            description = "선택한 카테고리(categoryId)의 템플릿 아이템 목록을 반환합니다."
    )
    @GetMapping("/categories/{categoryId}/template-items")
    public ResponseEntity<List<TemplateItemResponse>> getTemplateItems(
            @Parameter(description = "카테고리 ID", required = true, example = "1")
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(templateItemService.findByCategory(categoryId));
    }
}
