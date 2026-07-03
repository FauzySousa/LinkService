package com.fauzydev.linkservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fauzydev.linkservice.dto.request.UrlRequest;
import com.fauzydev.linkservice.dto.response.UrlResponse;
import com.fauzydev.linkservice.dto.response.ErrorResponse;
import com.fauzydev.linkservice.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/links")
@RequiredArgsConstructor
@Tag(name = "Encurtador de Links", description = "Endpoints para gerenciamento e geração de URLs encurtadas.")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/encurtar")
    @Operation(
        summary = "Encurtar uma URL longa", 
        description = "Recebe uma URL original, valida seu formato e retorna o link curto. Se a URL já tiver sido encurtada antes, reaproveita o registro existente no banco de dados."
    )
    @ApiResponse(
        responseCode = "201", 
        description = "Link encurtado gerado ou reaproveitado com sucesso.",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UrlResponse.class))
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Dados de entrada inválidos (URL vazia ou em formato incorreto).",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<UrlResponse> encurtarLink(@RequestBody @Valid UrlRequest request) {
        UrlResponse response = urlService.encurtarLink(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}