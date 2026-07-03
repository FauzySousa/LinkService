package com.fauzydev.linkservice.dto.request;

import org.hibernate.validator.constraints.URL;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UrlRequest(
    
    @NotBlank(message = "A URL original não pode estar vazia.")
    @URL(message = "Por favor, insira uma URL válida")
    @Schema(description = "URL original de formato longo que deseja encurtar", example = "https://github.com/josesilva/portfolio-backend-java/tree/main/projects/api")
    String urlOriginal
) {}