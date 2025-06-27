package com.packit.api.domain.trip.repository;

import com.packit.api.domain.trip.entity.Trip;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByUserId(Long userId);

    int countByUserId(Long userId);

    int countByUserIdAndIsCompletedTrue(Long userId);

    int countByUserIdAndIsCompletedFalse(Long userId);

    Optional<Trip> findByIdAndUserId(Long tripId, Long userId);

    @Query("""
    SELECT t FROM Trip t
    WHERE t.user.id = :userId
      AND t.isCompleted = false
      AND t.startDate >= :today
    ORDER BY t.startDate ASC
    LIMIT 1
""")
    Optional<Trip> findNearestCompletedTripAfterToday(@Param("userId") Long userId, @Param("today") LocalDate today);
}

