package com.fauzydev.linkservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Importe moderno aqui
import org.springframework.test.web.servlet.MockMvc;

import com.fauzydev.linkservice.entity.Url;
import com.fauzydev.linkservice.service.UrlService;

@WebMvcTest(UrlRedirecionarController.class)
class UrlRedirecionarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Substituiu o @MockBean antigo
    private UrlService urlService;

    @Test
    @DisplayName("Deve redirecionar para a URL original com sucesso")
    void deveRedirecionarParaUrlOriginal() throws Exception {
        // Arrange
        String codigo = "ABC123";
        Url url = new Url();
        url.setUrlCurta(codigo);
        url.setUrlOriginal("https://google.com");

        when(urlService.buscarPorCodigo(codigo)).thenReturn(url);

        // Act & Assert
        mockMvc.perform(get("/{urlCurta}", codigo))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://google.com"));
    }
}