package com.fauzydev.linkservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Importe moderno aqui
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fauzydev.linkservice.dto.request.UrlRequest;
import com.fauzydev.linkservice.dto.response.UrlResponse;
import com.fauzydev.linkservice.service.UrlService;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean // Substituiu o @MockBean antigo
    private UrlService urlService;

    @Test
    @DisplayName("Deve retornar Status 201 Created ao encurtar link com sucesso")
    void deveRetornarStatusCreatedAoEncurtar() throws Exception {
        // Arrange
        UrlRequest request = new UrlRequest("https://google.com");
        UrlResponse response = new UrlResponse("https://google.com", "http://localhost:8080/ABC123", LocalDateTime.now());

        when(urlService.encurtarLink(any(UrlRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/links/encurtar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.urlOriginal").value("https://google.com"))
                .andExpect(jsonPath("$.urlEncurtada").value("http://localhost:8080/ABC123"));
    }
}