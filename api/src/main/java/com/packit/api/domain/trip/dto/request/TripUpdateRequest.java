package com.packit.api.domain.trip.dto.request;

import com.packit.api.domain.trip.entity.TripType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record TripUpdateRequest(
        @NotBlank
        @Schema(description = "수정할 여행 제목", example = "제주도 가족 여행 - 일정 변경")
        String title,

        @NotBlank
        @Schema(description = "여행 지역", example = "제주도")
        String region,

        @NotNull
        @Schema(description = "여행 유형 (FAMILY, FRIEND, BUSINESS, OTHER)", example = "FAMILY")
        List<TripType> tripTypes,

        @NotNull
        @Schema(description = "수정된 여행 시작일 (yyyy-MM-dd)", example = "2025-07-02")
        LocalDate startDate,

        @NotNull
        @Schema(description = "수정된 여행 종료일 (yyyy-MM-dd)", example = "2025-07-08")
        LocalDate endDate,

        @Schema(description = "여행 설명 (선택)", example = "일정 변경으로 1일 뒤로 조정됨")
        String description
) {}
