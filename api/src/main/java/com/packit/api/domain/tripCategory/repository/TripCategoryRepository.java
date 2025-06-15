package com.packit.api.domain.tripCategory.repository;

import com.packit.api.domain.tripCategory.entity.TripCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripCategoryRepository extends JpaRepository<TripCategory, Long> {
    List<TripCategory> findAllByTripId(Long tripId);
}
