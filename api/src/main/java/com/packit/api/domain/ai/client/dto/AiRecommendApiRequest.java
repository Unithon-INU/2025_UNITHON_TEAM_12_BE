package com.packit.api.domain.ai.client.dto;

import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AiRecommendApiRequest {
    @Schema(description = "사용자 성별", example = "MALE")
    private String gender;

    @Schema(description = "사용자 나이", example = "28")
    private Integer age;

    @Schema(description = "여행 지역", example = "제주도")
    private String region;

    @Schema(description = "여행 시작일", example = "2025-07-01")
    private String startDate;

    @Schema(description = "여행 종료일", example = "2025-07-03")
    private String endDate;

    @Schema(description = "카테고리 이름", example = "의류")
    private String categoryName;

    public static AiRecommendApiRequest from(User user, Trip trip, TripCategory category) {
        return AiRecommendApiRequest.builder()
                .gender(user.getGender().name())  // enum → 문자열
                .age(user.getAge())
                .region(trip.getRegion())
                .startDate(trip.getStartDate().toString())  // LocalDate → String
                .endDate(trip.getEndDate().toString())
                .categoryName(category.getName())
                .build();
    }
}
