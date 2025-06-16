package com.packit.api.domain.tripItem.dto.request;

public record TripItemCreateRequest(
        String name,
        Integer quantity,
        String memo
) {}
