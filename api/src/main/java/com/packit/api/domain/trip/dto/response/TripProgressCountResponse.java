package com.packit.api.domain.trip.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TripProgressCountResponse(
        @Schema(description = "Trip ID", example = "1")
        Long tripId,

        @Schema(description = "전체 아이템 수", example = "15")
        int totalCount,

        @Schema(description = "체크된 아이템 수", example = "9")
        int checkedCount,

        @Schema(description = "진행률 (%)", example = "60")
        int progressPercent
) {
    public static TripProgressCountResponse of(Long tripId, int total, int checked) {
        int percent = total == 0 ? 0 : (int) Math.round((checked * 100.0) / total);
        return new TripProgressCountResponse(tripId, total, checked, percent);
    }
}
