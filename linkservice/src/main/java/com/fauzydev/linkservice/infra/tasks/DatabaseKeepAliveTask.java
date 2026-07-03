package com.fauzydev.linkservice.infra.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fauzydev.linkservice.repository.UrlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseKeepAliveTask {

    private final UrlRepository urlRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void keepDatabeseAlive(){
        log.info("Iniciando a query de keep-alive para manter o Supabase acordado...");

        try {
            
            long totalLinks = urlRepository.count();

            log.info("Query executada com sucesso! Total de links no banco: {}", totalLinks);
        } catch(Exception e) {
            log.error("Falha ao executar query de keep-alive no banco de dados", e);
        }
    }
}
