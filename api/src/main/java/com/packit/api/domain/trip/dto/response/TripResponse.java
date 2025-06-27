package com.packit.api.domain.trip.dto.response;

import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.trip.entity.TripType;

import java.time.LocalDate;
import java.util.List;

public record TripResponse(
        Long id,
        String title,
        String region,
        List<TripType> tripTypes,
        LocalDate startDate,
        LocalDate endDate,
        String description,
        boolean isCompleted
) {
    public static TripResponse from(Trip trip) {
        return new TripResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getRegion(),
                trip.getTripTypes(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getDescription(),
                trip.isCompleted()
        );
    }
}
