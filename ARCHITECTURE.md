## Architectural Decisions
I opted for a less complexity along the way, given the scope of the challenge, a modular monolith with clear boundaries and ownership.

## Code Structure Tree
I structured the codebase around the main business domains (accounts and transactions) with clear separation of concerns within each module (controller, service, repository, domain, etc.) and a shared module for cross-cutting concerns. This structure promotes modularity, maintainability, and scalability while keeping the code organized and easy to navigate.
Thinking on a simple volumetry and the scope of the challenge, I avoided over-engineering with too many layers or abstractions, while still following good design principles and patterns to ensure a clean and maintainable codebase.

```text
.
├── src
│   ├── main
│   │   ├── java/com/pismo/core
│   │   │   ├── account/            # account-related structure (controllers, services, repositories, entities)
│   │   │   │   ├── controller/         # account REST endpoints
│   │   │   │   │   └── docs/               # OpenAPI annotations/contracts for account endpoints
│   │   │   │   ├── usecase/            # account use cases (orchestrating business flows)
│   │   │   │   ├── service/            # account business logic
│   │   │   │   ├── repository/         # account persistence layer (Spring Data JPA)
│   │   │   │   └── domain/             # account domain model (DTO, entities, mappers, strategy)
│   │   │   │   │   ├── dto/                # DTOs for API contracts
│   │   │   │   │   │   ├── request/        # request DTOs for account endpoints
│   │   │   │   │   │   └── response/       # response DTOs for account endpoints
│   │   │   │   │   ├── entity/         # entities mapped to database tables
│   │   │   │   │   ├── mapper/         # mappers for converting between entities and DTOs
│   │   │   │   │   └── exception/      # custom exceptions related to accounts
│   │   │   ├── transaction/        # transaction-related structure (controllers, services, repositories, entities)
│   │   │   │   ├── controller/         # transaction REST endpoints
│   │   │   │   │   └── docs/               # OpenAPI annotations/contracts for transaction endpoints
│   │   │   │   ├── usecase/            # transaction use cases (orchestrating business flows)
│   │   │   │   ├── service/            # transaction business logic
│   │   │   │   ├── repository/         # transaction persistence layer (Spring Data JPA)
│   │   │   │   └── domain/             # transaction domain model (DTO, entities, mappers, strategy)
│   │   │   │   │   ├── dto/                # DTOs for API contracts
│   │   │   │   │   │   ├── request/        # request DTOs for transaction endpoints
│   │   │   │   │   │   └── response/       # response DTOs for transaction endpoints
│   │   │   │   │   ├── entity/         # entities mapped to database tables
│   │   │   │   │   ├── enums/          # enums for transactions (e.g., operation types)
│   │   │   │   │   ├── exception/      # custom exceptions related to transactions
│   │   │   │   │   ├── mapper/         # mappers for converting between entities and DTOs
│   │   │   │   │   ├── strategy/       # Strategy implementations for different operation types
│   │   │   │   │   ├── validation/     # custom validators for transaction rules
│   │   │   ├── shared/             # shared code across modules (utilities, common DTOs, etc.)
│   │   │   │   ├── config/             # cross-cutting concerns (global exception handling, etc.)
│   │   │   │   ├── exception/          # custom exceptions and handlers
│   │   │   │   ├── validation/         # custom validators and annotations
│   │   └── resources/              
│   │   │   ├── application.yaml    # main application configuration (actuator, datasource, etc.)
│   │   │   └── db/migration/       # Flyway SQL migrations, versioned
│   ├── test/                       # Unit tests
│   │   ├── java/com/pismo/core
│   │   │   ├── account/            # account-related unit tests
│   │   │   └──transaction/         # transaction-related unit tests
│   └── functionalTest/             # Functional tests simulating real use cases
│   │   ├── java/com/pismo/core
│   │   │   ├── account/            # account-related functional tests
│   │   │   └──transaction/         # transaction-related functional tests
│   │   └── resources/              
│   │   │    └── application-functional-test.yaml  # test-specific configuration (e.g., in-memory DB)
├── Dockerfile                      
├── docker-compose.yml              
└── build.gradle                   
```

### 1) Modular Monolith (vs. Microservices)

**Decision**: keep a modular monolith for this stage.

**Why**:
- Simpler deployment and observability.
- Lower coordination overhead.
- Faster iteration for current scope.

**Trade-offs acknowledged**:
- Difficult scaling (scale the whole app).
- Coupling can grow without module discipline.
- Deployment risk increases as codebase grows.

### 2) Relational Database

**Decision**: PostgreSQL as the system of record.

**Why**:
- Strong consistency and referential integrity.
- Good fit for transactional workflows.
- Mature ecosystem for migrations, backups, and monitoring.

### 3) API Style

**Decision**: REST endpoints for account and transaction workflows.

**Why**:
- Simple, interoperable HTTP contract.
- Easy integration and testing.
- Predictable request/response semantics.

### Design Patterns
- **Strategy Pattern**: operation-specific transaction amount handling (`purchase`, `withdrawal`, `payment`, etc.).
- **Repository Pattern**: persistence abstraction through Spring Data repositories.
- **Mapper Pattern**: explicit mapping between domain entities and API DTOs.
- **Use Case/Application Layer**: orchestration of business flows in dedicated use case classes.
- **Builder Pattern**: object creation for entities/DTOs with better readability.

## Implementation Practices

- **Database Migrations (Flyway)**: schema changes are versioned and applied in a controlled, repeatable way.
- **API Documentation (OpenAPI/Swagger)**: endpoints are documented for easier consumer integration.
- **Observability (Spring Actuator)**: health and operational endpoints support runtime monitoring.
- **Containerization (Docker / Docker Compose)**: consistent local and CI/runtime environment setup.
- **Externalized Configuration**: environment-specific values are injected via environment variables.
- **Build and Dependency Management (Gradle)**: centralized dependency and build lifecycle management.
- **Boilerplate Reduction (Lombok)**: reduces repetitive code for constructors/builders/getters.
- **Testing Strategy**: unit and functional tests cover core business and API behavior.

## Request Flow (Transaction Creation)

`POST /transactions`:
1. Controller validates input contract.
2. Use case validates account and resolves operation strategy.
3. Service persists transaction.
4. Repository writes through Spring Data JPA.
5. API returns `201 Created` with transaction payload.

## Naming Convention Decision

I recognize that PostgreSQL conventions commonly use lowercase/snake_case identifiers.

For this challenge, I intentionally followed the specification PDF naming **as-is** (e.g., `Accounts`, `Account_ID`, `OperationTypes`) to preserve strict requirement fidelity.

## Numeric Precision Decision

`Amount` is currently stored as `DECIMAL(10,2)` to match scope and keep implementation straightforward.

For production-grade financial systems, a wider precision/scale (for example, `DECIMAL(19,5)` or domain-specific money modeling) would usually be safer depending on currency and settlement rules.

### Evolution Path

**Principle**: keep the monolith now, split only when there is clear, measurable pain.

#### When should we consider splitting?
- One module requires much more scaling than the rest.
- Deployments become slow/risky because unrelated features are coupled.
- Team ownership becomes hard in a single codebase.

#### How to evolve safely (step by step)
1. **Harden module boundaries inside the monolith** (clear interfaces and ownership).
2. **Pick one candidate module** with real pressure (high change or high load).
3. **Extract only that module** into a service with a small, stable API contract.
4. **Keep data ownership explicit** and avoid shared-write tables across boundaries.
5. **Introduce async messaging only where needed**, not by default.

If none of the trigger conditions above happen, the modular monolith remains the preferred architecture.

