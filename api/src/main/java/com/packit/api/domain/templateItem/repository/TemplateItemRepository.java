package com.packit.api.domain.templateItem.repository;

import com.packit.api.domain.templateItem.entity.TemplateItem;
import com.packit.api.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateItemRepository extends JpaRepository<TemplateItem, Long> {
    List<TemplateItem> findAllByCategory(Category category);
}
