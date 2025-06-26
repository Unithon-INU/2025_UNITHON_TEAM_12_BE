package com.packit.api.domain.trip.repository;

import com.packit.api.domain.trip.entity.Trip;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByUserId(Long userId);

    int countByUserId(Long userId);

    int countByUserIdAndIsCompletedTrue(Long userId);

    int countByUserIdAndIsCompletedFalse(Long userId);

    Optional<Trip> findByIdAndUserId(Long tripId, Long userId);
}

