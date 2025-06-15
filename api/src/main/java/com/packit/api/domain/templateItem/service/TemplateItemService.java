package com.packit.api.domain.templateItem.service;

import com.packit.api.domain.category.entity.Category;
import com.packit.api.domain.category.repository.CategoryRepository;
import com.packit.api.domain.templateItem.dto.TemplateItemResponse;
import com.packit.api.domain.templateItem.entity.TemplateItem;
import com.packit.api.domain.templateItem.repository.TemplateItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateItemService {

    private final TemplateItemRepository templateItemRepository;
    private final CategoryRepository categoryRepository;

    public List<TemplateItemResponse> findByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. id=" + categoryId));
        List<TemplateItem> items = templateItemRepository.findAllByCategory(category);
        return items.stream()
                .map(TemplateItemResponse::from)
                .collect(Collectors.toList());
    }
}
