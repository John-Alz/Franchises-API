package com.api.franchises.infrastructure.entrypoints.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Franchises API")
                        .version("v1")
                        .description("API for managing franchises (WebFlux + Functional Endpoints)")
                        .contact(new Contact().name("John Angel").email("john@example.com")))
                .servers(List.of(
                        new Server().url("/").description("Default")
                ));
    }
}