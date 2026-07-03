package com.fauzydev.linkservice.dto.response;

import java.time.Instant;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(
    
    @Schema(description = "Momento exato em que o erro ocorreu", example = "2026-07-01T23:04:15.123Z")
    Instant timestamp,
    
    @Schema(description = "Código de status HTTP do erro", example = "400")
    Integer status,
    
    @Schema(description = "Descrição curta e padronizada do erro HTTP", example = "Bad Request")
    String error,
    
    @Schema(description = "Mensagem amigável ou detalhada explicando o motivo da falha", example = "Por favor, insira uma URL válida")
    String message,
    
    @Schema(description = "O caminho (URI) do endpoint onde o erro aconteceu", example = "/api/v1/links/encurtar")
    String path,
    
    @Schema(description = "Lista detalhada contendo erros específicos de validação de campos (se houver)")
    List<ValidationErrorDetais> erros
) {
    public ErrorResponse(Instant timestamp, Integer status, String error, String message, String path) {
        this(timestamp, status, error, message, path, null);
    }
}