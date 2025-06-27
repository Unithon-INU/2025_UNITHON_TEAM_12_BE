package com.packit.api.domain.ai.repository;

import com.packit.api.domain.ai.entity.AiRecommendationItemLog;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiRecommendationItemLogRepository extends JpaRepository<AiRecommendationItemLog, Long> {

    List<AiRecommendationItemLog> findByTripCategory(TripCategory category);
}
