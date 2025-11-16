# Architecture Overview

Comprehensive visual guide to the Epic Eats microservices architecture.

## System Context Diagram

High-level view of all the services and their interactions:

```mermaid
graph TB
    subgraph "External"
        CLIENT[Client Applications]
        KAFKA[Apache Kafka<br/>Message Broker]
    end

    subgraph "Microservices Ecosystem"
        ORDER[Order Service<br/>:8081]
        BILLING[Billing Service<br/>:8082]
        CUSTOMER[Customer Service<br/>:8083]
        RESTAURANT[Restaurant Service<br/>:8084]
        DELIVERY[Delivery Service<br/>:8085]
    end

    subgraph "Persistence"
        DB_ORDER[(Order DB)]
        DB_BILLING[(Billing DB)]
        DB_CUSTOMER[(Customer DB)]
        DB_RESTAURANT[(Restaurant DB)]
        DB_DELIVERY[(Delivery DB)]
    end

    CLIENT -->|REST API| ORDER
    CLIENT -->|REST API| BILLING
    CLIENT -->|REST API| CUSTOMER
    CLIENT -->|REST API| RESTAURANT
    CLIENT -->|REST API| DELIVERY

    ORDER <-->|Events| KAFKA
    BILLING <-->|Events| KAFKA
    CUSTOMER <-->|Events| KAFKA
    RESTAURANT <-->|Events| KAFKA
    DELIVERY <-->|Events| KAFKA

    ORDER --> DB_ORDER
    BILLING --> DB_BILLING
    CUSTOMER --> DB_CUSTOMER
    RESTAURANT --> DB_RESTAURANT
    DELIVERY --> DB_DELIVERY

    style ORDER fill:#e1f5ff
    style BILLING fill:#e1f5ff
    style CUSTOMER fill:#e1f5ff
    style RESTAURANT fill:#e1f5ff
    style DELIVERY fill:#e1f5ff
    style KAFKA fill:#fff4e1
```

**Key Characteristics:**
- **Independent deployment**: Each service runs on its own port (locally, not needed for production)
- **Separate databases**: Database per service pattern (no shared DB)
- **Event-driven communication**: Services communicate via Kafka, using Spring Cloud Stream
- **REST APIs**: Synchronous HTTP endpoints for external clients

## Hexagonal Architecture Pattern

Generic pattern used by all services:

```mermaid
graph TB
    subgraph "Inbound Side (Driving)"
        REST_A[REST Adapter<br/>HTTP Endpoints]
        MSG_A[Messaging Adapter<br/>Message Consumer]
    end

    subgraph "Ports (Interfaces)"
        REST_P[Inbound Ports<br/>Define Operations]
        MSG_P[Outbound Ports<br/>Define Dependencies]
    end

    subgraph "Core (Business Logic)"
        CORE[Service Layer<br/>Domain Logic<br/>Technology-Agnostic]
    end

    subgraph "Outbound Side (Driven)"
        DB_A[Persistence Adapter<br/>Database Access]
        MSG_OUT_A[Messaging Adapter<br/>Message Publisher]
    end

    REST_A -->|implements| REST_P
    MSG_A -->|implements| REST_P
    REST_P --> CORE
    CORE --> MSG_P
    MSG_P <-->|implements| DB_A
    MSG_P <-->|implements| MSG_OUT_A

    style CORE fill:#e1f5ff
    style REST_A fill:#ffe1e1
    style MSG_A fill:#ffe1e1
    style DB_A fill:#e1ffe1
    style MSG_OUT_A fill:#e1ffe1
```

**Benefits:**
- **Testability**: Core logic isolated from frameworks
- **Flexibility**: Easy to swap adapters (e.g., Kafka → RabbitMQ)
- **Maintainability**: Clear separation of concerns
- **Technology independence**: Core has zero framework dependencies

## Message Flow: Order Creation Saga

Complete event-driven workflow across all services:

```mermaid
sequenceDiagram
    participant Client
    participant Order as Order Service
    participant Billing as Billing Service
    participant Customer as Customer Service
    participant Restaurant as Restaurant Service
    participant Delivery as Delivery Service
    participant Kafka

    Client->>Order: POST /order (CreateOrder)
    Order->>Order: Status: CREATED
    Order->>Kafka: OrderCreatedEvent
    Order->>Kafka: ChargeOrderCommand

    Kafka->>Billing: ChargeOrderCommand
    Billing->>Billing: Process Payment
    Billing->>Kafka: OrderChargedEvent

    Kafka->>Order: OrderChargedEvent
    Order->>Order: Status: PAYED

    Kafka->>Customer: OrderChargedEvent
    Customer->>Kafka: ProcessOrderCommand

    Kafka->>Restaurant: ProcessOrderCommand
    Restaurant->>Restaurant: Prepare Food
    Restaurant->>Kafka: OrderProcessedEvent
    Restaurant->>Kafka: DeliverOrderCommand

    Kafka->>Order: OrderProcessedEvent
    Order->>Order: Status: PROCESSED

    Kafka->>Delivery: DeliverOrderCommand
    Delivery->>Delivery: Assign Driver
    Delivery->>Kafka: OrderDeliveredEvent

    Kafka->>Order: OrderDeliveredEvent
    Order->>Order: Status: DELIVERED

    Order-->>Client: Order Complete
```

**Pattern**: Saga with dual orchestration (Order Service + Customer Service)

## Service Responsibilities

| Service | Port | Primary Role | Key Events Published | Key Events Consumed |
|---------|------|--------------|---------------------|---------------------|
| **Order** | 8081 | Orchestrator & state tracker | OrderCreatedEvent, ChargeOrderCommand | OrderChargedEvent, OrderProcessedEvent, OrderDeliveredEvent |
| **Billing** | 8082 | Payment processor | OrderChargedEvent, OrderNotChargedEvent | ChargeOrderCommand |
| **Customer** | 8083 | Customer aggregator + orchestrator | CustomerCreatedEvent, ProcessOrderCommand | OrderChargedEvent, OrderDeliveredEvent |
| **Restaurant** | 8084 | Food preparation + delivery initiator | OrderProcessedEvent, DeliverOrderCommand | ProcessOrderCommand |
| **Delivery** | 8085 | Delivery execution | OrderDeliveredEvent | DeliverOrderCommand |

## Technology Stack Details

### Messaging: Spring Cloud Stream

All services use **[Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)** for broker-agnostic messaging:

```mermaid
graph LR
    subgraph "Service Code"
        BRIDGE[StreamBridge.send<br/>Consumer Bean]
    end

    subgraph "Spring Cloud Stream"
        ABSTRACTION[Broker Abstraction Layer]
    end

    subgraph "Binders (Pluggable)"
        KAFKA[Kafka Binder]
        RABBIT[RabbitMQ Binder]
        AZURE[Azure Service Bus]
        AWS[AWS Kinesis]
        GOOGLE[Google Pub/Sub]
    end

    BRIDGE --> ABSTRACTION
    ABSTRACTION --> KAFKA
    ABSTRACTION -.Alternative.-> RABBIT
    ABSTRACTION -.Alternative.-> AZURE
    ABSTRACTION -.Alternative.-> AWS
    ABSTRACTION -.Alternative.-> GOOGLE

    style ABSTRACTION fill:#fff4e1
    style KAFKA fill:#e1ffe1
```

**Current Configuration**: Kafka binder
**Easy Switching**: Change dependency from `spring-cloud-stream-binder-kafka` to any other binder

### Database Per Service

Each service owns its data:

```mermaid
graph TB
    subgraph "Service Boundaries"
        ORDER[Order Service] --> DB1[(Order Database<br/>Orders, OrderItems)]
        BILLING[Billing Service] --> DB2[(Billing Database<br/>Payments)]
        CUSTOMER[Customer Service] --> DB2[(Customer Database<br/>Customers)]
        RESTAURANT[Restaurant Service] --> DB3[(Restaurant Database<br/>Restaurants, Menus)]
        DELIVERY[Delivery Service] --> DB4[(Delivery Database<br/>Deliveries, Drivers)]
    end

    style ORDER fill:#e1f5ff
    style BILLING fill:#e1f5ff
    style CUSTOMER fill:#e1f5ff
    style RESTAURANT fill:#e1f5ff
    style DELIVERY fill:#e1f5ff
```

**Benefits**:
- Independent scaling
- Technology diversity (can use different DB types)
- Loose coupling
- Autonomous deployment

## Deployment View

Services and their runtime dependencies:

```mermaid
graph TB
    subgraph "Infrastructure"
        KAFKA_CLUSTER[Apache Kafka Cluster<br/>localhost:9092]
    end

    subgraph "Application Services"
        ORDER_SVC[order-service.jar<br/>Port: 8081<br/>DB: H2 in-memory]
        BILLING_SVC[billing-service.jar<br/>Port: 8082<br/>DB: H2 in-memory]
        CUSTOMER_SVC[customer-service.jar<br/>Port: 8083<br/>DB: H2 in-memory]
        RESTAURANT_SVC[restaurant-service.jar<br/>Port: 8084<br/>DB: H2 in-memory]
        DELIVERY_SVC[delivery-service.jar<br/>Port: 8085<br/>DB: H2 in-memory]
    end

    ORDER_SVC --> KAFKA_CLUSTER
    BILLING_SVC --> KAFKA_CLUSTER
    CUSTOMER_SVC --> KAFKA_CLUSTER
    RESTAURANT_SVC --> KAFKA_CLUSTER
    DELIVERY_SVC --> KAFKA_CLUSTER

    style KAFKA_CLUSTER fill:#fff4e1
```

**Runtime Requirements**:
- Java 17+
- Apache Kafka (localhost:9092)
- Maven (for building)

## Key Design Principles

### 1. Bounded Contexts (DDD)
Each service owns a specific business capability:
- Order → Order lifecycle
- Billing → Payments
- Customer → Customer profiles
- Restaurant → Menus & preparation
- Delivery → Logistics

### 2. Event-Driven Architecture
- **Commands**: Request actions (ChargeOrderCommand)
- **Events**: Announce important business events (OrderCreatedEvent)
- **Choreography**: Services react to events independently

### 3. Hexagonal Architecture
- Clear separation: Adapters ↔ Ports ↔ Core
- Framework isolation in adapters only
- Testable business logic

### 4. Database Per Service
- No shared databases
- Service owns its data schema
- Data consistency via events (eventual consistency)

## Training Path Through Architecture

**Day 1**: Focus on single service (hexagonal pattern)
- Study: [Hexagonal Architecture Example](../../architectural-examples/hexagonal-architecture-example/)
- Practice: Implement Menu Service

**Day 2**: Add communication (REST + Events)
- Study: [order-service](../../reference-services/order-service/README.md) ↔ [billing-service](../../reference-services/billing-service/README.md) interaction
- Practice: Event publishing and consuming

**Day 3**: Multi-service workflows (Sagas)
- Study: Full order creation flow (all 5 services)
- Practice: Saga compensation logic

**Day 4**: Production readiness
- Study: Testing strategies, circuit breakers
- Practice: Resilience patterns

## Related Documentation

- **[Services Communication](03-services-communication.md)** - Detailed message flows and service contracts
- **[Glossary](04-glossary.md)** - Architecture terms explained
- **[Kafka Setup](../setup/kafka-setup.md)** - Broker installation and configuration
- **Service READMEs** - Individual service architecture diagrams (order-service/, billing-service/, etc.)
