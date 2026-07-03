package com.fauzydev.linkservice.dto.response;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

public record UrlResponse(

    @Schema(description = "URL longa original que foi enviada pelo usuário", example = "https://github.com/josesilva/api")
    String urlOriginal,
    
    @Schema(description = "URL encurtada final gerada pelo sistema", example = "http://SEU_DOMÍNIO/r/x7B2a1")
    String urlEncurtada,
    
    @Schema(description = "Data e hora exata em que o registro foi criado no sistema", example = "2026-07-01T20:00:00")
    LocalDateTime dataCriacao
) {}