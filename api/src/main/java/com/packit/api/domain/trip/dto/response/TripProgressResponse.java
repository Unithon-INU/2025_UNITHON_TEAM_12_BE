package com.packit.api.domain.trip.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class TripProgressResponse {
    private int totalCategoryCount;
    private int completedCategoryCount;
    private double progressRate;

    public static TripProgressResponse of(long total, long completed, double rate) {
        return new TripProgressResponse((int) total, (int) completed, rate);
    }
}
