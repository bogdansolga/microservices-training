# Terminology Guide

Quick reference for understanding terms used in training materials vs. code implementation.

## Hexagonal Architecture Terms

| Term in Training | Term in Code | Explanation |
|-----------------|--------------|-------------|
| REST Adapter | `RestInboundAdapter` | HTTP API entry point (driving side) |
| Messaging Adapter (publisher) | `MessagingOutboundAdapter` | Kafka message publisher (driven side) |
| Messaging Adapter (consumer) | `MessagingInboundAdapter` | Kafka message consumer (driving side) |
| Database Adapter | `PersistenceOutboundAdapter` | Database access layer (driven side) |
| Business Logic | `OrderService`, `BillingService` | Core domain logic |
| Port | `RestInboundPort`, `MessagingOutboundPort` | Interface defining contracts |

## Inbound vs Outbound

### Inbound (Driving Side)
External requests coming **INTO** your service:
- REST API calls
- Kafka message consumption
- CLI commands
- Scheduled jobs

### Outbound (Driven Side)
Your service calling **OUT** to external dependencies:
- Database operations
- Kafka message publishing
- External API calls
- File system access

## Common Acronyms

| Acronym | Full Term | Explanation |
|---------|-----------|-------------|
| DTO | Data Transfer Object | Objects for transferring data between layers |
| CQRS | Command Query Responsibility Segregation | Separate read and write models |
| Saga | Long-running transaction pattern | Coordinates distributed transactions |
| DDD | Domain-Driven Design | Software design approach focused on domain model |

## Architecture Patterns

| Pattern | Description | Where Used |
|---------|-------------|------------|
| Hexagonal Architecture | Isolates business logic from external concerns | All reference services |
| Event-Driven | Services communicate via events | Inter-service communication |
| Saga | Distributed transaction management | Order creation workflow |
| CQRS | Separate read/write operations | Advanced patterns (Day 3) |

## Kafka Terms

| Term | Explanation |
|------|-------------|
| Topic | Stream of related events (e.g., "orders", "payments") |
| Producer | Service that publishes events to topics |
| Consumer | Service that subscribes to and processes events |
| Event | Message representing something that happened |

## Quick Lookup

**Looking for REST endpoints?** → Check `RestInboundAdapter.java`

**Looking for Kafka publishers?** → Check `MessagingOutboundAdapter.java`

**Looking for Kafka consumers?** → Check `MessagingInboundAdapter.java`

**Looking for business logic?** → Check service classes (e.g., `OrderService.java`)

**Looking for database code?** → Check `PersistenceOutboundAdapter.java` or repository classes
