package com.packit.api.domain.trip.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

public record TripSummaryResponse(
        @Schema(description = "전체 여행 수", example = "5")
        int totalCount,

        @Schema(description = "계획 중인 여행 수", example = "2")
        int plannedCount,

        @Schema(description = "완료된 여행 수", example = "3")
        int completedCount
) {
    public static TripSummaryResponse of(int total, int planned, int completed) {
        return new TripSummaryResponse(total, planned, completed);
    }
}