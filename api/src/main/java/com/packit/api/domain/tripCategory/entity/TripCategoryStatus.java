package com.packit.api.domain.tripCategory.entity;

import com.packit.api.common.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "짐싸기 상태")
public enum TripCategoryStatus {

    @Schema(description = "짐싸기를 시작하지 않음")
    NOT_STARTED("시작 전"),

    @Schema(description = "짐싸기 진행 중")
    IN_PROGRESS("진행 중"),

    @Schema(description = "짐싸기 완료")
    COMPLETED("완료");

    private final String displayName;
}