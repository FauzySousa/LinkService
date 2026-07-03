package com.fauzydev.linkservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Encurtador de Links premium API")
                .version("v1")
                .description("API REST para encurtamento inteligente de URLs com reaproveitamento de registros e redirecionamento de links.")
                .contact(new Contact()
                    .name("Fauzy Sousa")
                    .email("fauzydev9@gmail.com")));
    }
}
