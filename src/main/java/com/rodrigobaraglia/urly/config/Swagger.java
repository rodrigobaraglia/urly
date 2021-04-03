package com.rodrigobaraglia.urly.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Swagger {

    @Bean("urlyOpenApi")
    public OpenAPI urlyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Urly API")
                        .description("Url shortener")
                        .version("v0.0.1"));
    }
}
