# 🎬 Golden Raspberry API

API RESTful desenvolvida para análise dos vencedores do **Golden
Raspberry Awards (Razzies)**.

A aplicação lê automaticamente um arquivo CSV contendo os filmes
indicados e expõe um endpoint que retorna:

-   🟢 Produtores com **menor intervalo** entre vitórias consecutivas
-   🔴 Produtores com **maior intervalo** entre vitórias consecutivas

A solução foi construída com **Java 21 (LTS)**, **Spring Boot 3**,
arquitetura **Hexagonal (Ports & Adapters)**, testes de integração e
cobertura mínima de 90%.

------------------------------------------------------------------------

# 📌 Objetivo do Projeto

Dado um arquivo CSV com informações sobre filmes vencedores do prêmio
Golden Raspberry, a API deve:

-   Importar os dados automaticamente ao iniciar
-   Persistir em banco H2 em memória
-   Disponibilizar um endpoint REST
-   Retornar os produtores com maior e menor intervalo entre vitórias
    consecutivas

------------------------------------------------------------------------

# 🏗 Arquitetura

A aplicação segue o padrão **Arquitetura Hexagonal (Ports & Adapters)**,
garantindo:

-   Separação clara de responsabilidades
-   Baixo acoplamento
-   Alta testabilidade
-   Facilidade de evolução

## 📦 Estrutura de Pacotes

    outsera.goldenraspberry
     ├── domain
     ├── application
     │    ├── port.in
     │    └── port.out
     ├── adapter
     │    ├── in.web
     │    └── out
     │         ├── persistence
     │         └── ingestion
     └── bootstrap

### 🔹 Domain

Regras puras de negócio (cálculo de intervalos).

### 🔹 Application

Use cases e contratos (ports).

### 🔹 Adapters

Implementações externas: - REST Controllers - JPA / H2 - Leitura de CSV

### 🔹 Bootstrap

Inicialização automática do CSV ao subir a aplicação.

------------------------------------------------------------------------

# 🚀 Tecnologias Utilizadas

-   Java 21 (LTS)
-   Spring Boot 3.5.x
-   Maven
-   H2 Database (in-memory)
-   Spring Data JPA
-   Apache Commons CSV
-   Springdoc OpenAPI (Swagger)
-   JUnit 5
-   MockMvc
-   JaCoCo (\>= 90% cobertura)

------------------------------------------------------------------------

# 🧠 Boas Práticas Aplicadas

-   SOLID
-   Clean Code
-   Imutabilidade com `record`
-   Streams e Lambdas (Java moderno)
-   Tratamento global de exceções
-   API documentada com OpenAPI
-   GitFlow organizado

------------------------------------------------------------------------

# 📂 Importação de Dados

O arquivo CSV oficial deve estar em:

src/main/resources/movielist.csv

No startup a aplicação:

1.  Inicializa o banco H2
2.  Cria as tabelas automaticamente
3.  Importa o CSV
4.  Deixa a API pronta para uso

Configuração no `application.yml`:

``` yaml
app:
  ingestion:
    csv: classpath:movielist.csv
```

------------------------------------------------------------------------

# 🗄 Banco de Dados

Banco utilizado: **H2 em memória**

URL:

jdbc:h2:mem:goldenraspberry

Console disponível em:

http://localhost:8080/h2-console

Credenciais: - User: `sa` - Password: (vazio)

⚠️ O banco é apagado ao encerrar a aplicação.

------------------------------------------------------------------------

# 🌐 Endpoint Principal

## GET `/producers/intervals`

### Exemplo de resposta:

``` json
{
  "min": [
    {
      "producer": "Producer Name",
      "interval": 1,
      "previousWin": 2008,
      "followingWin": 2009
    }
  ],
  "max": [
    {
      "producer": "Another Producer",
      "interval": 99,
      "previousWin": 1900,
      "followingWin": 1999
    }
  ]
}
```

------------------------------------------------------------------------

# 📖 Swagger / OpenAPI

Documentação disponível em:

http://localhost:8080/swagger-ui.html

ou

http://localhost:8080/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8080/v3/api-docs

------------------------------------------------------------------------

# 🛡 Tratamento Global de Exceções

Implementado com `@RestControllerAdvice`.

Formato padrão de erro:

``` json
{
  "timestamp": "2026-02-24T14:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation error",
  "path": "/producers/intervals"
}
```

Cobertura de:

-   Validação
-   JSON inválido
-   CSV inválido
-   Arquivo inexistente
-   Erros internos
-   Violação de integridade

------------------------------------------------------------------------

# 🧪 Testes

A aplicação utiliza **somente testes de integração**, conforme
solicitado no desafio.

-   `@SpringBootTest`
-   `MockMvc`
-   H2 em memória
-   CSV isolado para testes

Cobertura mínima configurada:

> = 90% linhas

Para rodar testes:

mvn clean test

Relatório JaCoCo:

target/site/jacoco/index.html

------------------------------------------------------------------------

# 🔄 GitFlow Utilizado

Branches:

-   `main` → versão estável
-   `develop` → integração
-   `feature/*` → novas funcionalidades
-   `release/*` → preparação de versão
-   `hotfix/*` → correções urgentes

Padrão de commits:

-   `feat:`
-   `fix:`
-   `test:`
-   `refactor:`
-   `chore:`
-   `docs:`

------------------------------------------------------------------------

# ▶️ Como Executar

## Build

mvn clean install

## Rodar aplicação

mvn spring-boot:run

Ou

java -jar target/golden-raspberry-api-0.0.1-SNAPSHOT.jar

------------------------------------------------------------------------

# 👨‍💻 Autor

Nicolas Pires\
Projeto desenvolvido como avaliação técnica backend.
