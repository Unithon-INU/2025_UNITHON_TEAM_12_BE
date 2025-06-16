package com.packit.api.domain.trip.entity;

import com.packit.api.common.BaseTimeEntity;
import com.packit.api.domain.trip.dto.request.TripCreateRequest;
import com.packit.api.domain.trip.dto.request.TripUpdateRequest;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;
    private String region;

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    private LocalDate startDate;
    private LocalDate endDate;

    private String description;

    private boolean isCompleted;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TripCategory> tripCategories = new ArrayList<>();

    @Builder
    private Trip(User user, String title, String region, TripType tripType,
                 LocalDate startDate, LocalDate endDate, String description) {
        this.user = user;
        this.title = title;
        this.region = region;
        this.tripType = tripType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.isCompleted = false;
    }

    public static Trip of(User user, TripCreateRequest request) {
        return Trip.builder()
                .user(user)
                .title(request.title())
                .region(request.region())
                .tripType(request.tripType())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .description(request.description())
                .build();
    }
    public void update(TripUpdateRequest request) {
        this.title = request.title();
        this.region = request.region();
        this.tripType = request.tripType();
        this.startDate = request.startDate();
        this.endDate = request.endDate();
        this.description = request.description();
    }
}
