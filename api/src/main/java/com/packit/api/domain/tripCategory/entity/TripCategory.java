package com.packit.api.domain.tripCategory.entity;

import com.packit.api.common.BaseTimeEntity;
import com.packit.api.domain.category.entity.Category;
import com.packit.api.domain.trip.entity.Trip;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category; // nullable

    private String name;

    @Enumerated(EnumType.STRING)
    private TripCategoryStatus status;

    @Builder
    private TripCategory(Trip trip, Category category, String name, TripCategoryStatus status) {
        this.trip = trip;
        this.category = category;
        this.name = name;
        this.status = status;
    }

    public static TripCategory of(Trip trip, Category category, String name) {
        return TripCategory.builder()
                .trip(trip)
                .category(category) // null 가능
                .name(name)
                .status(TripCategoryStatus.NOT_STARTED)
                .build();
    }

    public void updateStatus(TripCategoryStatus status) {
        this.status = status;
    }


}
