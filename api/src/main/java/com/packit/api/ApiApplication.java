package com.packit.api;

import com.packit.api.common.config.AiRecommendProperties;
import com.packit.api.common.dto.PermitAllProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({
		PermitAllProperties.class,
		AiRecommendProperties.class
})
@EnableJpaAuditing
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
