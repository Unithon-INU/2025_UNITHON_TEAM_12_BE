package com.packit.api.domain.TemplateItem.entity;

import com.packit.api.common.Gender;
import com.packit.api.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TemplateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String name;

    private Integer defaultQuantity;

    @Enumerated(EnumType.STRING)
    private Gender gender; // MALE, FEMALE, BOTH

    @Builder
    private TemplateItem(Category category, String name, Integer defaultQuantity, Gender gender) {
        this.category = category;
        this.name = name;
        this.defaultQuantity = defaultQuantity;
        this.gender = gender;
    }

    public static TemplateItem of(Category category, String name, int defaultQuantity, Gender gender) {
        return TemplateItem.builder()
                .category(category)
                .name(name)
                .defaultQuantity(defaultQuantity)
                .gender(gender)
                .build();
    }
}
