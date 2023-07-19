package com.paweu.autofleet.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class OpenApiDocs {

    private final VersionHolder versionHolder;

    @Bean
    public OpenAPI openApiMain(){
        return new OpenAPI()
                .info(new Info()
                        .title("Autofleet")
                        .description("Documentation for Autofleet API")
                        .version(versionHolder.getVersion())
                )
                .components(
                        new Components()
                                .addSecuritySchemes("bearer-key",
                                        new SecurityScheme()
                                                .name("bearer-key")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .in(SecurityScheme.In.HEADER)
                                )
                );
    }

    @Bean
    public GroupedOpenApi openApiMainGroup(){
        return GroupedOpenApi.builder()
                .group("main")
                .displayName("Main API")
                .packagesToScan("com.paweu.autofleet")
                .build();
    }
}
