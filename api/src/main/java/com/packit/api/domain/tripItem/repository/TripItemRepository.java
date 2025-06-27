package com.packit.api.domain.tripItem.repository;

import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripItem.entity.TripItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripItemRepository extends JpaRepository<TripItem, Long> {
    List<TripItem> findAllByTripCategoryId(Long tripCategoryId);

    List<TripItem> findAllByTripCategory(TripCategory category);

    @Query("SELECT ti FROM TripItem ti " +
            "JOIN ti.tripCategory tc " +
            "JOIN tc.trip t " +
            "WHERE t.id = :tripId")
    List<TripItem> findAllByTripId(Long tripId);
}
