package com.fauzydev.linkservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fauzydev.linkservice.dto.request.UrlRequest;
import com.fauzydev.linkservice.dto.response.UrlResponse;
import com.fauzydev.linkservice.entity.Url;
import com.fauzydev.linkservice.mapper.UrlMapper;
import com.fauzydev.linkservice.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlMapper urlMapper;
    private final UrlRepository urlRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlResponse encurtarLink(UrlRequest request) {
       
        Optional<Url> urlExistente = urlRepository.findByUrlOriginal(request.urlOriginal());

        if (urlExistente.isPresent()) {
            Url urlSalva = urlExistente.get(); 

            String urlEncurtadaCompleta = baseUrl + urlSalva.getUrlCurta();

            return new UrlResponse(
                urlSalva.getUrlOriginal(),
                urlEncurtadaCompleta,
                urlSalva.getDataCriacao()
            );
        }
       
        String codigoCurto;

        do {
            codigoCurto = RandomStringUtils.secure().nextAlphanumeric(6);
        } while(urlRepository.existsByUrlCurta(codigoCurto));

        Url url = urlMapper.toEntity(request);

        url.setUrlCurta(codigoCurto);
        url.setDataCriacao(LocalDateTime.now());

        Url urlSalva = urlRepository.save(url);

        String urlEncurtadaCompleta = baseUrl + urlSalva.getUrlCurta();

        return new UrlResponse(
            urlSalva.getUrlOriginal(),
            urlEncurtadaCompleta,
            urlSalva.getDataCriacao()
        );
    }

    public Url buscarPorCodigo(String codigoCurto) {
        return urlRepository.findByUrlCurta(codigoCurto).
            orElseThrow(() -> new RuntimeException("O link curto informado não foi encontrado ou já expirou."));
    }
}
