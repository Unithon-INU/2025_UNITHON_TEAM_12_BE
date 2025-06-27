package com.packit.api.common.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "ai.recommendation")
public class AiRecommendProperties {

    @NotBlank
    private String url; // 예: http://localhost:8000/ai/recommend
}
