# Hexagonal Architecture Example

## Purpose
**Simplified reference implementation** for Day 1 training exercises.
Demonstrates hexagonal architecture (also known as Ports and Adapters pattern) with minimal complexity.

This example is intentionally simple to keep the focus on **architectural structure** rather than business complexity.

## What This Example Shows

### The Problem (Traditional Layered Architecture)
- Controllers directly call repositories
- Business logic mixed with framework code
- Hard to test without full Spring context
- Technology changes require business logic changes

### The Solution (Hexagonal Architecture)
- Adapters isolated at boundaries
- Business logic has ZERO framework dependencies
- Easy to test with simple mocks
- Can swap REST for gRPC without touching business logic

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      INBOUND (Driving Side)                     │
│                                                                 │
│  ┌──────────────┐          ┌──────────────┐                     │
│  │ RestAdapter  │─────────▶│   RestPort   │                     │
│  │  (HTTP/Web)  │          │ (Interface)  │                     │
│  └──────────────┘          └──────────────┘                     │
│   Spring @RestController          │                             │
│                                   │                             │
└───────────────────────────────────┼─────────────────────────────┘
                                    │
                                    ▼
                    ┌───────────────────────────────┐
                    │    CORE (Business Logic)      │
                    │                               │
                    │  ┌─────────────────────────┐  │
                    │  │ BusinessLogicService    │  │
                    │  │  (Domain Logic)         │  │
                    │  └─────────────────────────┘  │
                    │                               │
                    └───────────────────────────────┘
                                    │
                                    ▼
┌───────────────────────────────────┼─────────────────────────────┐
│                                   │                             │
│  ┌──────────────┐          ┌──────────────┐                     │
│  │MessagingPort │◀─────────│MessagingPort │                     │
│  │ (Interface)  │          │   Adapter    │                     │
│  └──────────────┘          └──────────────┘                     │
│         │                   Kafka/Messaging                     │
│         │                                                       │
│         │                  OUTBOUND (Driven Side)               │
└─────────┴───────────────────────────────────────────────────────┘
```

**Key Concept:** Dependencies point INWARD toward business logic.
- Outer layers (adapters) depend on inner layers (ports)
- Inner layers (business logic) NEVER depend on outer layers

## File-by-File Explanation

### Inbound Side (Driving)

#### `RestAdapter.java` - HTTP Entry Point
```
Location: inbound/adapter/RestAdapter.java
```
- Uses `@RestController` (Spring framework)
- Depends on `RestPort` (interface, NOT implementation)
- **Key Learning:** Adapter knows about framework, port does not

**What it does:**
1. Receives HTTP GET request to `/hexagonal/{value}`
2. Delegates to `RestPort` interface
3. Returns HTTP response

#### `RestPort.java` - Inbound Contract
```
Location: inbound/port/RestPort.java
```
- Pure Java class with Spring annotations for dependency injection
- Acts as intermediary between adapter and business logic
- Defines WHAT operations exist, not HOW

**What it does:**
1. Receives request from `RestAdapter`
2. Delegates to `BusinessLogicService`
3. Returns result

### Core (Business Logic)

#### `BusinessLogicService.java` - Domain Logic
```
Location: service/BusinessLogicService.java
```
- `@Service` is only Spring dependency (for DI)
- Implements business operations
- Depends on `MessagingPort` (outbound contract)
- **Key Learning:** Depends on interfaces, not implementations

**What it does:**
1. Performs business operation (the "magic")
2. Publishes event via `MessagingPort`
3. Returns result

### Outbound Side (Driven)

#### `MessagingPort.java` - Outbound Contract
```
Location: outbound/port/MessagingPort.java
```
- Pure Java class
- Business logic calls this, adapter implements it
- **Key Learning:** Port defines contract, adapter provides implementation

#### `MessagingAdapter.java` - Messaging Implementation
```
Location: outbound/adapter/MessagingAdapter.java
```
- Contains framework-specific code (simplified for demo)
- Implements messaging operations
- **Key Learning:** Can be swapped for RabbitMQ adapter without changing business logic

## Request Flow

```
HTTP GET /hexagonal/test
         │
         ▼
    RestAdapter
         │
         ▼
      RestPort
         │
         ▼
 BusinessLogicService ────────────┐
         │                        │
         │                        ▼
         │                 MessagingPort
         │                        │
         │                        ▼
         │                MessagingAdapter
         │                        │
         ▼                        ▼
  Return "The request      Publish message
   value is 'test'"        "OrderCreated"
```

## How to Use This Example (Day 1 Training)

### Step 1: Study the Structure (10 minutes)
1. Open each file in this order:
   - `RestAdapter.java` → See how HTTP enters the system
   - `RestPort.java` → See how adapter connects to business logic
   - `BusinessLogicService.java` → See pure business logic
   - `MessagingPort.java` → See outbound contract
   - `MessagingAdapter.java` → See external dependency

2. Notice the dependency arrows:
   - Adapter → Port (interface)
   - Service → Port (interface)
   - Port ← Adapter (implementation)

### Step 2: Trace a Request (5 minutes)
1. Start at `RestAdapter.java` line 27
2. Follow the method call chain
3. Notice: business logic never knows about HTTP or messaging frameworks

### Step 3: Compare with Monolith (10 minutes)
1. Open `monolithic-application/OrderService.java`
2. Compare direct dependencies vs interface-based dependencies
3. Notice transaction boundaries and coupling

### Step 4: Implement Menu Service (Day 1 Exercise 3)
Use this structure as your template:
- Create similar package structure
- Define your ports (interfaces)
- Implement business logic
- Add adapters for REST and persistence

## Running the Example

### Prerequisites
- Java 17+
- Maven

### Start the Application
```bash
cd architectural-examples/hexagonal-architecture-example
mvn spring-boot:run
```

### Test the Endpoint
```bash
curl http://localhost:8080/hexagonal/test
```

Expected response:
```json
The request value is 'test'
```

Check console output:
```
Performing the application 'magic'...
Publishing the message 'OrderCreated'...
```

## What's Intentionally Simple

To keep focus on architecture, this example:
- **No database** - Keeps focus on architecture, not data access
- **No complex business logic** - Focus on structure, not domain
- **No authentication** - One concept at a time
- **Simplified messaging** - Console output instead of real message broker (Kafka in production examples)

## Common Questions

### Why is RestPort a class, not an interface?
In this simplified example, `RestPort` is a concrete class for simplicity. In production code (see `reference-services/order-service`), you would typically use interfaces for ports.

### Why both Port AND Adapter?
- **Port** = Contract (what operations are available)
- **Adapter** = Implementation (how to execute those operations)
- Separation allows swapping implementations without changing contracts

### Can I have multiple adapters for one port?
Yes! You could have:
- `RestAdapter` (HTTP)
- `GraphQLAdapter` (GraphQL)
- `CLIAdapter` (Command-line)

All implementing the same `RestPort` interface.

## Next Steps

After understanding this example:

1. **Study production implementation:**
   - `reference-services/order-service/` - Full hexagonal architecture
   - Notice additional patterns: DTOs, mappers, error handling

2. **Implement Menu Service (Day 1 Exercise 3):**
   - Use this structure as template
   - Add your domain logic
   - Practice separating concerns

3. **Explore messaging (Day 2):**
   - See how message broker integration works in `order-service` (uses Kafka via Spring Cloud Stream)
   - Understand event-driven communication

## Related Training Materials
- **Day 1 Exercise 2:** Uses this as primary reference for understanding hexagonal architecture
- **Day 1 Exercise 3:** Template for implementing Menu Service
- **docs/03-service-communication.md:** System-level architecture overview

## Key Takeaways

1. **Dependency Rule:** Dependencies point inward toward business logic
2. **Testability:** Business logic can be tested without Spring, HTTP, or messaging frameworks
3. **Flexibility:** Adapters can be swapped without changing business logic (e.g., switch message brokers)
4. **Separation of Concerns:** Each layer has a single, clear responsibility
