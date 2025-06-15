package com.packit.api.domain.trip.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "여행 유형 (FAMILY: 가족여행, FRIEND: 친구여행, BUSINESS: 출장, OTHER: 기타)")
public enum TripType {

    @Schema(description = "가족 여행")
    FAMILY("가족"),

    @Schema(description = "친구와의 여행")
    FRIEND("친구"),

    @Schema(description = "출장")
    BUSINESS("출장"),

    @Schema(description = "기타")
    OTHER("기타");

    private final String displayName;
}
