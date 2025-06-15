package com.packit.api.domain.tripItem.entity;

import com.packit.api.common.BaseTimeEntity;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TripCategory tripCategory;

    private String name;

    private Integer quantity;

    private boolean isChecked;

    private boolean isSaved;

    private boolean isAiGenerated;

    private String memo;

    @Builder
    private TripItem(TripCategory tripCategory, String name, Integer quantity,
                     boolean isChecked, boolean isSaved, boolean isAiGenerated, String memo) {
        this.tripCategory = tripCategory;
        this.name = name;
        this.quantity = quantity;
        this.isChecked = isChecked;
        this.isSaved = isSaved;
        this.isAiGenerated = isAiGenerated;
        this.memo = memo;
    }

    public static TripItem of(TripCategory tripCategory, String name, Integer quantity) {
        return TripItem.builder()
                .tripCategory(tripCategory)
                .name(name)
                .quantity(quantity)
                .isChecked(false)
                .isSaved(true)
                .isAiGenerated(false)
                .memo(null)
                .build();
    }

    public void toggleCheck() {
        this.isChecked = !this.isChecked;
    }

    public void update(String name, Integer quantity, String memo) {
        this.name = name;
        this.quantity = quantity;
        this.memo = memo;
    }
}
