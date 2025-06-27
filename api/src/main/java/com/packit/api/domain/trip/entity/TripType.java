package com.packit.api.domain.trip.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
@Schema(description = "여행 유형 (각 항목은 사용자의 여행 상황이나 특성을 설명)")
public enum TripType {

    @Schema(description = "해외 여행")
    OVERSEAS("해외"),

    @Schema(description = "여름철 여행")
    SUMMER("여름"),

    @Schema(description = "겨울철 여행")
    WINTER("겨울"),

    @Schema(description = "비 오는 날씨 여행")
    RAINY("비오는날씨"),

    @Schema(description = "생리 기간 중 여행")
    MENSTRUAL_PERIOD("생리기간"),

    @Schema(description = "아이 동반 여행")
    WITH_CHILD("아이 동반"),

    @Schema(description = "호텔 숙박 여행")
    HOTEL("호텔 숙박");

    private final String displayName;

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static TripType from(String value) {
        return Stream.of(TripType.values())
                .filter(e -> e.displayName.equals(value) || e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 TripType: " + value));
    }
}
