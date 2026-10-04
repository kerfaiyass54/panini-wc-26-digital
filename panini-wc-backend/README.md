# Panini WC Backend

This is the main backend service for the Panini World Cup 2026 digital collection application. It provides a REST API for managing stickers, user collections, trades, and more.

## Tech Stack

*   **Java 21**
*   **Spring Boot**
*   **Spring Data JPA**
*   **Spring Kafka**
*   **Spring Security**
*   **PostgreSQL**
*   **MongoDB**
*   **Keycloak** (for authentication and authorization)

## Setup

1.  **Configure `application.properties`:**

    Create an `application.properties` file in `src/main/resources`. You can use the provided `application.properties.example` as a template.

    This file contains the configuration for the database, Kafka, Keycloak, and other services.

2.  **Run the application:**

    You can run the application using your IDE or with the following Maven command:

    ```bash
    ./mvnw spring-boot:run
    ```

## API Documentation

The API documentation is available via Swagger UI. Once the application is running, you can access it at:

[http://localhost:9090/swagger-ui.html](http://localhost:9090/swagger-ui.html)