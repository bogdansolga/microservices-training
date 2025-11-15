# Delivery Service

## Purpose
Manages delivery assignments, tracking, and logistics for food orders.

## Architecture
**Pattern:** Hexagonal Architecture (Ports & Adapters)

```mermaid
graph TB
    subgraph "Inbound Adapters (Driving)"
        REST[REST Controller<br/>:8085/delivery]
        KAFKA_IN[Kafka Consumer<br/>Delivery Commands]
    end

    subgraph "Business Logic"
        DELIVERY[Delivery Service<br/>Assignment & Tracking]
    end

    subgraph "Outbound Adapters (Driven)"
        DB[(Database<br/>Delivery Data)]
        KAFKA_OUT[Kafka Producer<br/>Delivery Events]
    end

    REST --> DELIVERY
    KAFKA_IN --> DELIVERY
    DELIVERY --> DB
    DELIVERY --> KAFKA_OUT

    style DELIVERY fill:#e1f5ff
    style REST fill:#ffe1e1
    style KAFKA_IN fill:#ffe1e1
    style DB fill:#e1ffe1
    style KAFKA_OUT fill:#e1ffe1
```

### Inbound (Driving)
- **REST API** - HTTP endpoints on port 8085
- **Kafka Consumer** - Order and delivery events

### Business Logic
- Delivery assignment and tracking logic

### Outbound (Driven)
- **Database** - Delivery data persistence
- **Kafka Producer** - Delivery status events

## API Endpoints

```
POST   /delivery/assign    - Assign delivery driver
GET    /delivery/{id}      - Track delivery status
PUT    /delivery/{id}      - Update delivery status
```

## Events Published

- `DeliveryAssignedEvent` - When driver assigned
- `DeliveryCompletedEvent` - When delivery finished

## Events Consumed

- `OrderCreatedEvent` - From order-service
- `PaymentCompletedEvent` - From billing-service

## Running

```bash
cd reference-services/delivery-service
mvn spring-boot:run
```

Access: http://localhost:8085

## Related Training Materials
- **Day 3:** Saga compensation patterns
- **docs/Microservices interactions.md:** Event choreography
