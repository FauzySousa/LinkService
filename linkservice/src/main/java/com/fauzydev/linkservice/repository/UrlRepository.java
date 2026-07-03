package com.fauzydev.linkservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fauzydev.linkservice.entity.Url;


public interface UrlRepository extends JpaRepository<Url,Long>{

    Optional<Url> findByUrlCurta(String codigoCurto);

    Optional<Url> findByUrlOriginal(String urlOriginal);

    boolean existsByUrlCurta(String codigoCurto);
}
