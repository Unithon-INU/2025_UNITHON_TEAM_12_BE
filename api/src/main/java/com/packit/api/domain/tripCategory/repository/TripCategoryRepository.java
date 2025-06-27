package com.packit.api.domain.tripCategory.repository;

import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripCategoryRepository extends JpaRepository<TripCategory, Long> {
    List<TripCategory> findAllByTripId(Long tripId);

    List<TripCategory> findAllByTrip(Trip trip);

    Optional<TripCategory> findByIdAndTripId(Long tripCategoryId, Long tripId);
}
