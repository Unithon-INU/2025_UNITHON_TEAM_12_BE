package com.packit.api.domain.ai.entity;

import com.packit.api.domain.tripCategory.entity.TripCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class AiRecommendationItemLog {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TripCategory tripCategory;

    private String name;
    private int quantity;

    private LocalDateTime createdAt;

    @Builder
    public AiRecommendationItemLog(TripCategory tripCategory, String name, int quantity, LocalDateTime createdAt) {
        this.tripCategory = tripCategory;
        this.name = name;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }
}
