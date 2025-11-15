# Microservices Training

Hands-on examples for learning microservices architecture through a food ordering platform built with Spring Boot and event-driven patterns.

## Project Overview

A complete microservices ecosystem demonstrating event-driven architecture (EDA) with CQRS using Spring Cloud Stream and Kafka.

**Domain**: Food ordering system spanning order placement through delivery
**Architecture**: Hexagonal architecture with inbound/outbound adapters per service
**Messaging**: Kafka-based event streaming with command-event chains

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

- Spring Boot 3.5.7
- Spring Cloud Stream 4.3.0
- Apache Kafka
- Java 17
- H2 Database
