# Microservices Training

Hands-on examples for learning microservices architecture through a food ordering platform built with Spring Boot and event-driven patterns.

## Project Overview

A complete microservices ecosystem demonstrating event-driven architecture (EDA) with CQRS using [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream) with the Kafka binder.

**Domain**: Food ordering system spanning order placement through delivery
**Architecture**: Hexagonal architecture with inbound/outbound adapters per service
**Messaging**: Broker-agnostic event streaming using Spring Cloud Stream (currently configured with Kafka)

## Modules

### Reference Services
Complete, working implementations demonstrating best practices:
- **common-support**: Shared message definitions and parent classes
- **order-service** (8081): Order lifecycle orchestrator and state tracker
- **billing-service** (8082): Payment processing
- **customer-service** (8083): Customer data aggregation and restaurant orchestration
- **restaurant-service** (8084): Food preparation and delivery initiation
- **delivery-service** (8085): Delivery execution

### Workshop Services
Starting templates for hands-on exercises:
- **workshop-order-service**: Order service implementation exercise
- **workshop-billing-service**: Billing service implementation exercise
- **workshop-restaurant-service**: Restaurant service implementation exercise

### Additional Modules
- **monolithic-application**: Comparison monolith implementation
- **architectural-examples**: Architecture pattern demonstrations

## Key Patterns

**Event-Driven Orchestration**: Dual orchestration between Order and Customer services
**Message Flow**: Commands trigger actions → Events notify completion → Next commands initiated
**Order Lifecycle**: CREATED → PAYED → PROCESSED → DELIVERED

See `Microservices interactions.md` for complete message flows and service interactions.

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
- **[Architecture Overview](docs/architecture/01-architecture-overview.md)** - Visual diagrams: system context, hexagonal pattern, message flows, deployment architecture
- **[Infrastructure Abstraction](docs/architecture/02-infrastructure-abstraction.md)** - How we achieve vendor independence with JPA and Spring Cloud Stream (for beginners)
- **[Service Communication](docs/architecture/03-service-communication.md)** - Complete event flows, service responsibilities, and message types across the microservices architecture
- **[Glossary](docs/architecture/04-glossary.md)** - Architecture terms and naming conventions explained
- **[Kafka Setup](docs/setup/kafka-setup.md)** - Installation guide and topic creation commands for running Kafka locally

### Hands-On Exercises (4-Day Training)
- **[Day 1: Service Decomposition & Hexagonal Architecture](docs/exercises/day-1.md)** - Analyze a monolith, identify Bounded Contexts, implement a Hexagonal Architecture
- **[Day 2: Inter-Process Communication](docs/exercises/day-2.md)** - Build REST APIs (synchronous) and implement Event-Driven messaging with Kafka (asynchronous)
- **[Day 3: Data Management & Sagas](docs/exercises/day-3.md)** - Design and implement distributed transactions using the Saga pattern and CQRS
- **[Day 4: Testing & Production Readiness](docs/exercises/day-4.md)** - Add resilience patterns (circuit breaker, retry, rate limiting), implement comprehensive testing, prepare for production
