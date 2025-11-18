# Microservices Training

Hands-on examples for learning microservices architecture through a food ordering platform built with Spring Boot and event-driven patterns.

## Project Overview

A microservices architecture example, using an Event-Driven Architecture (EDA) and CQRS. The async communication uses [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream) with the Kafka binder.

**Domain**: Food ordering, from order placement to delivery
**Architecture**: Hexagonal Architecture with inbound/outbound adapters and ports in each service
**Messaging**: Broker-agnostic event streaming, using Spring Cloud Stream (currently configured with Kafka)

## Modules

### Reference Services
A Microservices Architecture example, containing working implementations and a few best practices:
- **common-support**: Shared message definitions and parent classes for all services
- **[order-service](reference-services/order-service/)** (8081): Order lifecycle orchestrator and state tracker
- **[billing-service](reference-services/billing-service/)** (8082): Payments processing
- **[customer-service](reference-services/customer-service/)** (8083): Customer data aggregation
- **[restaurant-service](reference-services/restaurant-service/)** (8084): Restaurant orchestration, food preparation and delivery initiation
- **[delivery-service](reference-services/delivery-service/)** (8085): Delivery execution

### Workshop Services
Starting templates for hands-on exercises:
- **[workshop-order-service](workshop-services/workshop-order-service/)**: Order service implementation exercise
- **[workshop-billing-service](workshop-services/workshop-billing-service/)**: Billing service implementation exercise
- **[workshop-restaurant-service](workshop-services/workshop-restaurant-service/)**: Restaurant service implementation exercise

### Additional Modules
- **[monolithic-application](monolithic-application/)**: Comparison monolith implementation
- **[architectural-examples](architectural-examples/)**: Architecture pattern demonstrations
  - **[hexagonal-architecture-example](architectural-examples/hexagonal-architecture-example/)**: Simplified hexagonal architecture reference
  - **[graphql-example](architectural-examples/graphql-example/)**: GraphQL API implementation example

## Key Patterns

**Event-Driven Orchestration**: Dual orchestration between Order and Customer services
**Message Flow**: Commands trigger actions → Events notify completion → Next commands initiated
**Order Lifecycle**: CREATED → PAYED → PROCESSED → DELIVERED

See [Services Communication](docs/architecture/03-services-communication.md) for the end-to-end services interactions.

## Technology Stack

- **Spring Boot** 3.5.7
- **[Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)** 4.3.0 - Broker-agnostic messaging abstraction
- **Kafka Binder** - Currently configured (easily switchable to RabbitMQ, Azure Service Bus, AWS Kinesis, etc.)
- **Apache Kafka** - Message broker (default configuration)
- **Java** 17
- **H2 Database** - In-memory database

### Why Spring Cloud Stream?

Spring Cloud Stream provides a **broker-agnostic abstraction** layer over messaging systems. All examples use Spring Cloud Stream, which means:

- **Easy broker switching**: Change from Kafka to RabbitMQ, Azure Service Bus, AWS Kinesis, or Google Pub/Sub by simply changing dependencies and configuration
- **Consistent programming model**: Same code works across different brokers
- **Production flexibility**: Start with Kafka in dev, switch to managed services in production without code changes

**Supported binders**: Kafka, RabbitMQ, Azure Service Bus, AWS Kinesis, Google Pub/Sub, Apache RocketMQ, and more.

Learn more: [Spring Cloud Stream Documentation](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/)

## Training Materials

### Documentation
- **[Architecture Overview](docs/architecture/01-overview.md)** - Visual diagrams: system context, hexagonal architecture, message flows, deployment architecture
- **[Hexagonal Architecture Implementation](docs/architecture/HEXAGONAL_ARCHITECTURE.md)** - Complete guide to hexagonal architecture implementation across all services
- **[Migration Guide](docs/MIGRATION_GUIDE.md)** - Step-by-step guide for migrating services to hexagonal architecture
- **[Infrastructure Abstraction](docs/architecture/02-infrastructure-abstraction.md)** - Implementation independence design, using JPA and Spring Cloud Stream
- **[Services Communication](docs/architecture/03-services-communication.md)** - Detailed services responsibilities and messages exchanges, across the microservices architecture example
- **[Glossary](docs/architecture/04-glossary.md)** - Architecture terms and naming conventions explained
- **[Kafka Setup](docs/setup/kafka-setup.md)** - Installation guide and topic creation commands, for running Kafka locally

### Hands-On Exercises (4-Day Training)
- **[Day 1: Services Decomposition & Hexagonal Architecture](docs/exercises/day-1.md)** - Analyze a monolith, identify Bounded Contexts, implement a Hexagonal Architecture
- **[Day 2: Inter-Process Communication](docs/exercises/day-2.md)** - Build REST APIs (synchronous) and implement Event-Driven messaging with Kafka (asynchronous)
- **[Day 3: Data Management & Sagas](docs/exercises/day-3.md)** - Design and implement distributed transactions using the Saga pattern and CQRS
- **[Day 4: Testing & Production Readiness](docs/exercises/day-4.md)** - Add resilience patterns (circuit breaker, retry, rate limiting), implement comprehensive testing, prepare for production
