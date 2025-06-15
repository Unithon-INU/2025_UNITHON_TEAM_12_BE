package com.packit.api.domain.tripItem.repository;

import com.packit.api.domain.tripItem.entity.TripItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripItemRepository extends JpaRepository<TripItem, Long> {
    List<TripItem> findAllByTripCategoryId(Long tripCategoryId);
}
