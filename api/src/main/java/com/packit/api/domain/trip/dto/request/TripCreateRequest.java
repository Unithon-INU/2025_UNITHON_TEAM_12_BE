package com.packit.api.domain.trip.dto.request;

import com.packit.api.domain.trip.entity.TripType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "여행 생성 요청 DTO")
public record TripCreateRequest(

        @Schema(description = "여행 제목", example = "제주도 가족 여행")
        String title,

        @Schema(description = "여행 지역", example = "제주도")
        String region,

        @Schema(description = "여행 유형 목록 (복수 선택 가능)", example = "[\"OVERSEAS\", \"SUMMER\"]")
        List<TripType> tripTypes,

        @Schema(description = "여행 시작일 (yyyy-MM-dd)", example = "2025-07-01")
        LocalDate startDate,

        @Schema(description = "여행 종료일 (yyyy-MM-dd)", example = "2025-07-07")
        LocalDate endDate,

        @Schema(description = "여행 설명 (선택)", example = "6박 7일 동안 가족과 함께하는 여행입니다.")
        String description
) {}