package com.fauzydev.linkservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "urls")
@Getter
@Setter
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_original", nullable = false, columnDefinition = "TEXT")
    private String urlOriginal;

    @Column(name = "url_curta", nullable = false, unique = true, length = 10)
    private String urlCurta;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

}
