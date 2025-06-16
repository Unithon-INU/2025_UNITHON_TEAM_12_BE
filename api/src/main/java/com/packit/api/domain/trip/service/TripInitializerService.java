package com.packit.api.domain.trip.service;

import com.packit.api.domain.templateItem.repository.TemplateItemRepository;
import com.packit.api.domain.category.entity.Category;
import com.packit.api.domain.category.repository.CategoryRepository;
import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.repository.TripCategoryRepository;
import com.packit.api.domain.tripItem.repository.TripItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripInitializerService {

    private final CategoryRepository categoryRepository;
    private final TemplateItemRepository templateItemRepository;
    private final TripCategoryRepository tripCategoryRepository;
    private final TripItemRepository tripItemRepository;

    public void initializeDefaultsFor(Trip trip) {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            TripCategory tripCategory = TripCategory.of(trip, category, category.getName());
            tripCategoryRepository.save(tripCategory);
        }
    }
}
