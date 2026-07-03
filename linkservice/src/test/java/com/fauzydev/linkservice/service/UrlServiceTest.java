package com.fauzydev.linkservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fauzydev.linkservice.dto.request.UrlRequest;
import com.fauzydev.linkservice.dto.response.UrlResponse;
import com.fauzydev.linkservice.entity.Url;
import com.fauzydev.linkservice.mapper.UrlMapper;
import com.fauzydev.linkservice.repository.UrlRepository;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlMapper urlMapper;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        // Injeta o valor da propriedade ${app.base-url} manualmente para o teste
        ReflectionTestUtils.setField(urlService, "baseUrl", "http://localhost:8080/");
    }

    @Test
    @DisplayName("Deve encurtar um link com sucesso")
    void deveEncurtarLinkComSucesso() {
        // Arrange
        UrlRequest request = new UrlRequest("https://google.com");
        Url urlEsperada = new Url();
        urlEsperada.setUrlOriginal("https://google.com");
        urlEsperada.setUrlCurta("ABC123");
        urlEsperada.setDataCriacao(LocalDateTime.now());

        when(urlRepository.existsByUrlCurta(anyString())).thenReturn(false);
        when(urlMapper.toEntity(request)).thenReturn(urlEsperada);
        when(urlRepository.save(any(Url.class))).thenReturn(urlEsperada);

        // Act
        UrlResponse response = urlService.encurtarLink(request);

        // Assert
        assertNotNull(response);
        assertEquals("https://google.com", response.urlOriginal());
        assertTrue(response.urlEncurtada().contains("http://localhost:8080/"));
        verify(urlRepository, times(1)).save(any(Url.class));
    }

    @Test
    @DisplayName("Deve buscar uma URL por código com sucesso")
    void deveBuscarPorCodigoComSucesso() {
        // Arrange
        String codigo = "ABC123";
        Url url = new Url();
        url.setUrlCurta(codigo);
        url.setUrlOriginal("https://google.com");

        when(urlRepository.findByUrlCurta(codigo)).thenReturn(Optional.of(url));

        // Act
        Url resultado = urlService.buscarPorCodigo(codigo);

        // Assert
        assertNotNull(resultado);
        assertEquals("https://google.com", resultado.getUrlOriginal());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o código não for encontrado")
    void deveLancarExcecaoQuandoCodigoNaoEncontrado() {
        // Arrange
        String codigo = "INVALIDO";
        when(urlRepository.findByUrlCurta(codigo)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            urlService.buscarPorCodigo(codigo);
        });

        assertEquals("O link curto informado não foi encontrado ou já expirou.", exception.getMessage());
    }
}