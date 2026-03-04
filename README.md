# Employee API Case Study

Spring Boot case study project for managing employees with a small CRUD API.

## Stack

- Java 21+
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Liquibase
- OpenAPI
- MapStruct
- Cucumber + Testcontainers for integration tests

## What It Does

- Exposes CRUD endpoints for employees under `/employee`
- Generates the API interface and DTOs from [`src/main/resources/api/api.yaml`](/Users/andrei/Desktop/OwnProjects/casestudy-api/src/main/resources/api/api.yaml)
- Starts PostgreSQL from [`docker-compose.yml`](/Users/andrei/Desktop/OwnProjects/casestudy-api/docker-compose.yml) when the app starts locally
- Applies Liquibase migrations on startup, including seed data
- Returns structured API errors for validation, not found, and duplicate `personnelNumber` cases

Requirements:

- JDK 21 or newer
- Docker running

Swagger UI:

- `http://localhost:8080/swagger-ui.html`

OpenAPI JSON:

- `http://localhost:8080/v3/api-docs`

## Run Tests

```bash
mvn test
```

The integration tests use PostgreSQL Testcontainers and run the real application stack against a temporary database.
