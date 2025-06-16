package com.packit.api.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
//@EnableWebMvc
@RequiredArgsConstructor
public class SwaggerConfig {

    @Value("${swagger.base-url}")
    private String swaggerBaseUrl;

    @Bean
    public OpenAPI customOpenAPI() {

        Info info = new Info()
                .title("myNutrition API Documentation")
                .description("myNutrition API 명세서")
                .version("v1.0.0");

        // Bearer Auth 설정
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Bearer Auth를 사용하는 Security Requirement 설정
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("JWT");

        return new OpenAPI()
                .info(info)
                .security(List.of(securityRequirement))
                .components(new Components().addSecuritySchemes("JWT", securityScheme))
                .servers(List.of(
                        new Server().url(swaggerBaseUrl).description("Active Profile Server"), //prod server
                        new Server().url("http://localhost:8080").description("Local Server") // Local Server

                ));
    }


    // ForwardedHeaderFilter Bean 등록 Nginx 프록시 서버 사용 시 필요
    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

}
