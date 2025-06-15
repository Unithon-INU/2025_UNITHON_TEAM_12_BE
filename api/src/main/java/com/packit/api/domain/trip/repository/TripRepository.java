package com.packit.api.domain.trip.repository;

import com.packit.api.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByUserId(Long userId);
}
