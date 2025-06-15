package com.packit.api.domain.templateItem.dto;

import com.packit.api.common.Gender;
import com.packit.api.domain.templateItem.entity.TemplateItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class TemplateItemResponse {

    @Schema(description = "템플릿 아이템 ID")
    private Long id;

    @Schema(description = "템플릿 아이템 이름")
    private String name;

    @Schema(description = "기본 수량")
    private int defaultQuantity;

    @Schema(description = "대상 성별")
    private Gender gender;

    public static TemplateItemResponse from(TemplateItem item) {
        return of(item.getId(), item.getName(), item.getDefaultQuantity(), item.getGender());
    }
}
