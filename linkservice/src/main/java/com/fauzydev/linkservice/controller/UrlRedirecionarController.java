package com.fauzydev.linkservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fauzydev.linkservice.entity.Url;
import com.fauzydev.linkservice.service.UrlService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UrlRedirecionarController {

    private final UrlService urlService;

    @GetMapping("/{urlCurta}")
    public String redirecionar(@PathVariable String urlCurta) {
      
        Url url = urlService.buscarPorCodigo(urlCurta);

        return "redirect:" + url.getUrlOriginal();
    }
}
