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
    HOTEL("호텔 숙박"),

    @Schema(description = "힐링 여행")
    HEALING("힐링"),

    @Schema(description = "레저/액티비티 여행")
    LEISURE("레저"),

    @Schema(description = "가족과 함께하는 여행")
    FAMILY("가족"),

    @Schema(description = "친구와 함께하는 여행")
    FRIEND("친구"),

    @Schema(description = "연인과 함께하는 여행")
    COUPLE("연인"),

    @Schema(description = "관광 중심의 여행")
    SIGHTSEEING("관광"),

    @Schema(description = "먹는 것이 중심인 여행")
    FOOD("식도락"),

    @Schema(description = "등산 중심의 여행")
    HIKING("등산"),

    @Schema(description = "장거리 여행 (국내 or 해외)")
    LONG_DISTANCE("장거리");

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
