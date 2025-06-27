package com.packit.api.domain.ai.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.trip.entity.TripType;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AiRecommendApiRequest {

    @Schema(description = "사용자 성별", example = "MALE")
    private String gender;

    @Schema(description = "사용자 나이", example = "28")
    private Integer age;

    @Schema(description = "여행 제목", example = "제주도")
    private String title;

    @Schema(description = "여행 지역", example = "제주도")
    private String region;

    @Schema(description = "여행 시작일", example = "2025-07-01")
    private String startDate;

    @Schema(description = "여행 종료일", example = "2025-07-03")
    private String endDate;

    @Schema(description = "여행 설명", example = "보원이랑 예나랑 제욱이랑 햄버거파티하러 미국간다")
    private String description;

    @Schema(description = "여행 유형 태그 목록", example = "[\"여름\", \"해외\", \"아이 동반\"]")
    @JsonProperty("tripType") // ✅ FastAPI 요구 형식에 맞춤
    private List<String> tripTypes;

    public static AiRecommendApiRequest from(User user, Trip trip) {
        return AiRecommendApiRequest.builder()
                .gender(user.getGender().name())
                .age(user.getAge())
                .title(trip.getTitle())
                .region(trip.getRegion())
                .startDate(trip.getStartDate().toString())
                .endDate(trip.getEndDate().toString())
                .description(trip.getDescription())
                .tripTypes(trip.getTripTypes().stream()
                        .map(TripType::getDisplayName)
                        .toList())
                .build();
    }
}
