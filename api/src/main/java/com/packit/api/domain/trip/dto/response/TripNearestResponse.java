package com.packit.api.domain.trip.dto.response;

import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.trip.entity.TripType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TripNearestResponse(
        Long tripId,
        String title,
        String region,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        TripType tripType,
        int progressRate
) {
    public static TripNearestResponse from(Trip trip, int totalCount, int checkedCount) {
        int progressRate = (totalCount == 0) ? 0 : (int) ((checkedCount * 100.0) / totalCount);
        return TripNearestResponse.builder()
                .tripId(trip.getId())
                .title(trip.getTitle())
                .region(trip.getRegion())
                .description(trip.getDescription())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .tripType(trip.getTripType())
                .progressRate(progressRate)
                .build();
    }
}
