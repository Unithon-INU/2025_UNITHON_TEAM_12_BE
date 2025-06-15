package com.packit.api.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Configuration
@Component
@ConfigurationProperties(prefix = "security.permit-all")
public class PermitAllProperties {
    private List<String> urls;
}