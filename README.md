# Transaction Engine

This is a simple transaction engine implemented in Java 21 with Spring Boot. It supports operations such as purchase, installment purchase, withdrawal, and payment, managing accounts and transactions.

The goal of this project is to implement a simple transaction engine that can handle various types of transactions (purchase, installment purchase, withdrawal, payment) while managing accounts and ensuring data integrity. The application should be able to create accounts, perform transactions, and retrieve account details.

The application is designed as a modular monolith. For more details, refer to [ARCHITECTURE.md](ARCHITECTURE.md).

---
## Technologies Used
* **Java 21** (as the programming language)
* **Spring Boot** (with Spring Data JPA for database interactions and Spring Web for REST API)
* **PostgreSQL** (as the relational database)
* **Docker & Docker Compose** (for containerization, to facilitate deployment and ensure consistency across environments)
* **JUnit 5** (for unit testing)
* **Mockito** (for unit testing)
* **Swagger/OpenAPI** (for API documentation)
* **Spring Actuator** (for monitoring and health checks)
* **Gradle** (build and dependency management)
* **Lombok** (to reduce boilerplate code)
* **Flyway** (Database Migrations, to facilitate schema management and versioning)

---
## Features

* Create accounts
* Perform transactions (purchase, installment purchase, withdrawal, payment)
* Retrieve account details

---

## Getting Started

### Prerequisites

* Docker
* Docker Compose

---

## Configuration

This application follows the **Externalized Configuration** principle from the Twelve-Factor App methodology.

All sensitive and environment-specific configurations (such as database credentials) are managed through environment variables instead of being hardcoded in the application.

A `.env` file is used for local development convenience and is loaded via Docker Compose.

---

### Setup

To avoid exposing sensitive information, the application uses environment variables.

1. Create a `.env` file:

```bash
cp env.example .env
```

2. Fill in the required values based on `.env.example`.

---

### Run the Application

```bash
docker-compose up --build -d
```

The application will be available at:

```
http://localhost:8080
```

---

### Stop and Cleanup

```bash
docker-compose down -v
```

---

## Testing Strategy

This project follows the **Testing Pyramid** approach:

* **Unit Tests**: Validate individual components in isolation (fast and reliable)
* **Functional Tests**: Validate end-to-end behavior simulating real use cases

### Run tests

```bash
./gradlew test
./gradlew functionalTest
```

---

# API Overview

### Account Endpoints
After starting containers with Docker, you can use _Swagger_ or _CURL_ to test the application manually:

* `POST /accounts` — Create an account
    ```bash
    curl -X 'POST' \
      'http://localhost:8080/accounts' \
      -H 'accept: */*' \
      -H 'Content-Type: application/json' \
      -d '{
      "document_number": "12345678900"
    }'
    ```
* `GET /accounts/{account_id}` — Retrieve an account
    ```bash
    curl -X 'GET' \
      'http://localhost:8080/accounts/{id}' \
      -H 'accept: */*'
    ```

### Transaction Endpoint

* `POST /transactions` — Perform transaction
    ```bash
    curl -X 'POST' \
    'http://localhost:8080/transactions' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "account_id": 1,
    "operation_type_id": 1,
    "amount": 100
    }'
    ```
## Data Model

### Account

| Account_ID  | Document_Number |
|-------------|-----------------|
| BIGSERIAL   | VARCHAR(20)     |

### Transaction

| Transaction_ID | Account_ID | OperationType_ID | Amount        | EventDate |
|----------------|------------|------------------|---------------|-----------|
| BIGSERIAL      | BIGSERIAL  | BIGINT           | Decimal(10,2) | TIMESTAMP |

### Operation Type

| OperationType_ID | Description  |
|------------------|--------------|
| BIGINT           | VARCHAR(255) |

---

## Resources

* Swagger UI: http://localhost:8080/swagger-ui/index.html
* Architecture: [ARCHITECTURE.md](ARCHITECTURE.md)
* Actuator: http://localhost:8081/actuator

---

## Possible Improvements

### Balance Management
- Introduce explicit account balance tracking and authorization rules.
- Implement locking mechanisms (e.g., optimistic or pessimistic locking) to ensure consistency under concurrent access.

### Idempotency
- Add idempotency keys on operations to prevent duplicate transaction creation.