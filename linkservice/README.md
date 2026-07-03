# 🔗 Encurtador de Links Premium API

<p align="center">
  <img src="src/main/resources/static/images/favicon.png" alt="Logo do Projeto" width="80" height="80" style="border-radius: 16px;"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.7-brightgreen?style=for-the-badge&logo=spring" alt="Spring Boot 3.4.7">
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql" alt="MySQL">
  <img src="https://img.shields.io/badge/Swagger-OpenAPI%203-lightgreen?style=for-the-badge&logo=swagger" alt="Swagger">
</p>

Uma solução backend robusta desenvolvida para o encurtamento inteligente de URLs longas, combinando uma API RESTful de alta performance com uma interface web fluida e responsiva renderizada via server-side. O projeto prioriza a eficiência de infraestrutura, utilizando um algoritmo focado na economia de escrita em banco de dados e no reaproveitamento inteligente de registros.

---

## 🛠️ Stack Tecnológica & Decisões de Arquitetura

A escolha das ferramentas reflete uma preocupação genuína com padrões de mercado, performance e facilidade de manutenção:

* **Java 21 (LTS):** Aproveitando recursos modernos de concorrência e a imutabilidade nativa dos **Java Records** para trafegar dados de forma segura.
* **Spring Boot 3.4.7:** Utilização do ecossistema moderno do Spring (Data JPA, Web e Validation) como núcleo da aplicação.
* **Thymeleaf:** Integração server-side nativa para renderizar as telas sem o overhead de frameworks SPA complexos, mantendo o carregamento instantâneo.
* **Flyway Migrations:** Garantia de evolução controlada e versionamento do schema do banco de dados (MySQL), evitando scripts manuais.
* **MapStruct & Lombok:** Mapeamento em tempo de compilação livre de reflexão (reflection), separando de forma limpa as entidades de banco dos objetos de transferência de dados (DTOs).
* **SpringDoc OpenAPI v2:** Documentação viva e interativa gerada automaticamente a partir do código.

---

## ⚙️ Engenharia do Projeto e Regras de Negócio

### 1. Inteligência no Encurtamento e Idempotência
A aplicação protege a camada de persistência contra o crescimento desordenado de dados. Antes de gerar um novo link curto, o serviço realiza um lookup indexado:
* **URL já cadastrada:** O sistema intercepta o fluxo e retorna instantaneamente o registro existente, agindo de forma idempotente e poupando armazenamento.
* **Nova URL:** Inicia-se um laço otimizado com `RandomStringUtils.secure()` para gerar um token alfanumérico único de 6 caracteres. A unicidade é garantida via checagem preventiva no JPA antes do commit.

### 2. Redirecionamento de Baixa Latência (HTTP 302)
O fluxo de redirecionamento foi isolado em um `@Controller` leve e dedicado. Ele captura o token diretamente da raiz da URI, executa a busca por índice e responde ao navegador com um redirecionamento limpo, minimizando o tempo de resposta percebido pelo usuário final.

### 3. Tratamento Resiliente de Erros (`Global Exception Handler`)
O comportamento da API é totalmente previsível graças à centralização de exceções com `@RestControllerAdvice`. Em vez de estourar stacktraces no cliente, a aplicação devolve payloads limpos e estruturados (`ErrorResponse`), convertendo erros de validação de campos (`MethodArgumentNotValidException`) em mensagens amigáveis baseadas em padrões internacionais da RFC 7807.

### 4. Manutenção de Pool de Conexões (`Keep-Alive Task`)
Pensando em infraestruturas Cloud modernas onde instâncias de banco de dados podem entrar em estado de suspensão ou *cold start* por inatividade (como ocorre em camadas gratuitas de serviços como Supabase, Render ou Neon), foi implementada uma rotina assíncrona com `@Scheduled`. Diariamente, uma query leve de contagem (`count()`) mantém a conexão quente e o pool do HikariCP sempre pronto.

---

## 📁 Organização e Estrutura de Pacotes

O código segue padrões rígidos de separação de responsabilidades, garantindo alta coesão e facilidade de escala:

```text
com.fauzydev.linkservice
├── config          # Configurações globais da aplicação e beans do Swagger
├── controller      # Pontos de entrada (Endpoints REST e rotas de redirecionamento)
├── dto
│   ├── request     # Payload de entrada validado via anotações Jakarta (ex: @URL)
│   └── response    # Modelagem das respostas de sucesso e exceções estruturadas
├── entity          # Modelos de domínio persistidos via ORM JPA
├── exception       # Exceções customizadas e interceptador global de erros
├── infra.tasks     # Rotinas automatizadas em segundo plano (Keep-Alive)
├── mapper          # Contratos do MapStruct para desacoplamento de entidades/DTOs
├── repository      # Camada de abstração de dados e comunicação com o MySQL
└── service         # Núcleo de regras de negócio e geração de hashes seguros