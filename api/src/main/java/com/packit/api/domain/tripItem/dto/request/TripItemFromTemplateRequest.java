package com.packit.api.domain.tripItem.dto.request;

import java.util.List;

public record TripItemFromTemplateRequest (
    List<Long> templateItemIds
) {}
