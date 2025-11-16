# Terminology Guide

Quick reference for understanding terms used in training materials vs. code implementation.

## Hexagonal Architecture Terms

| Term in Training | Term in Code | Explanation |
|-----------------|--------------|-------------|
| REST Adapter | `RestInboundAdapter` | HTTP API entry point (driving side) |
| Messaging Adapter (publisher) | `MessagingOutboundAdapter` | Message publisher - sends events to broker (driven side) |
| Messaging Adapter (consumer) | `MessagingInboundAdapter` | Message consumer - receives events from broker (driving side) |
| Database Adapter | `PersistenceOutboundAdapter` | Database access layer (driven side) |
| Business Logic | `OrderService`, `BillingService` | Core domain logic |
| Port | `RestInboundPort`, `MessagingOutboundPort` | Interface defining contracts |

**Note:** Messaging adapters are broker-agnostic. We use **Kafka** via Spring Cloud Stream (see main README.md), but the adapters work with any supported broker (RabbitMQ, Azure Service Bus, AWS Kinesis, etc.).

## Inbound vs Outbound

### Inbound (Driving Side)
External requests coming **INTO** your service:
- REST API calls
- Message consumption from broker (Kafka in our examples)
- CLI commands
- Scheduled jobs

### Outbound (Driven Side)
Your service calling **OUT** to external dependencies:
- Database operations
- Message publishing to broker (Kafka in our examples)
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

## Messaging Terms

**Current Broker:** Kafka (via Spring Cloud Stream - easily switchable to other brokers)

| Term | Explanation |
|------|-------------|
| Broker | Message middleware (Kafka, RabbitMQ, Azure Service Bus, etc.) |
| Topic/Queue | Stream of related events (e.g., "orders", "payments") |
| Producer/Publisher | Service that publishes events to topics |
| Consumer/Subscriber | Service that subscribes to and processes events |
| Event/Message | Message representing something that happened |

**See also:** Main README.md for Spring Cloud Stream broker flexibility

## Quick Lookup

**Looking for REST endpoints?** → Check `RestInboundAdapter.java`

**Looking for message publishers?** → Check `MessagingOutboundAdapter.java` (publishes to broker)

**Looking for message consumers?** → Check `MessagingInboundAdapter.java` (consumes from broker)

**Looking for business logic?** → Check service classes (e.g., `OrderService.java`)

**Looking for database code?** → Check `PersistenceOutboundAdapter.java` or repository classes

**Note:** All messaging uses Spring Cloud Stream, currently configured with Kafka binder. See main README.md for broker switching details.
