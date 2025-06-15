package com.packit.api.domain.TemplateItem.repository;

import com.packit.api.domain.TemplateItem.entity.TemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateItemRepository extends JpaRepository<TemplateItem, Long> {}
