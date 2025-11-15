# Day 1 - Hands-On Exercises
**Focus: Service Decomposition & Hexagonal Architecture**

---

## Exercise 1: Monolith Service Decomposition (~45-60 min)

### Objective
Analyze the Epic Eats monolithic application to identify bounded contexts and potential microservices.

### Part A: Code Analysis (~20 min)
1. Explore `monolithic-application/src/main/java/com/epic/eats/`
2. Identify key domain entities: Order, Customer, Restaurant, MenuItem, Payment
3. Analyze `OrderService.java` (lines 26-28, 42-72):
   - What dependencies does OrderService have?
   - What happens during order creation?
4. Identify coupling points: direct service calls, shared entities, transaction boundaries

### Part B: Services Identification (~25 min)
Working in 1-2 groups:
1. Draw a context map with bounded contexts
2. For each proposed service define:
   - **Responsibility**: What business capability?
   - **Data**: Which entities does it own?
   - **Dependencies**: What does it need from others?

**Suggested Services:** Order, Billing/Payment, Customer, Restaurant/Menu, Delivery

### Part C: Discussion (~10-15 min)
- Present your decomposition (5/10 min per group)
- Where would you draw boundaries and why?
- What challenges do you foresee?

---

## Exercise 2: Hexagonal Architecture Exploration (~30-45 min)

### Objective
Understand hexagonal architecture by comparing monolith vs. microservices structure.

### Recommended Study Path
**Before starting this exercise:**
1. Open `architectural-examples/hexagonal-architecture-example/`
2. Read the README.md (comprehensive explanation with diagrams)
3. Study the code structure (10 minutes)
4. Run the example locally and test it
5. Then proceed to compare with order-service below

This simplified example will help you understand the pattern before analyzing the production code.

### Part A: Identify Layers (~15 min)

**Monolithic Application:**
- Controller → Service → Repository layers

**Order Service Microservice:**
- **Inbound Adapters**: `reference-services/order-service/.../inbound/adapter/`
  - `RestInboundAdapter.java` - REST API
  - `MessagingInboundAdapter.java` - Kafka consumer
- **Inbound Ports**: Interfaces defining how to interact with business logic
- **Business Logic**: `OrderService.java` - core domain logic
- **Outbound Ports**: Interfaces for external dependencies
- **Outbound Adapters**: `.../outbound/adapter/`
  - `PersistenceOutboundAdapter.java` - database
  - `MessagingOutboundAdapter.java` - Kafka publisher

### Part B: Draw the Architecture (20 min)

Create a diagram showing:
```
        ┌─────────────────────────────────┐
        │   INBOUND ADAPTERS (Drivers)    │
        │   - REST Controller             │
        │   - Message Consumer            │
        └──────────────┬──────────────────┘
                       │ Ports (interfaces)
        ┌──────────────▼──────────────────┐
        │    BUSINESS LOGIC (Core)        │
        │      OrderService               │
        └──────────────┬──────────────────┘
                       │ Ports (interfaces)
        ┌──────────────▼──────────────────┐
        │   OUTBOUND ADAPTERS (Driven)    │
        │   - Database Repository         │
        │   - Message Publisher           │
        └─────────────────────────────────┘
```

Label:
- Which parts change frequently? (adapters)
- Which parts remain stable? (business logic)
- Dependency direction? (arrows point inward)

### Part C: Discussion (~10 min)
- Why adapters instead of direct dependencies?
- How does this enable testing?
- How does this enable technology changes?

---

## Exercise 3: Implement Hexagonal Service (~45-60 min)

### Objective
Create a Menu Service using hexagonal architecture.

### Scenario
Build a **Menu Service** to manage restaurant menu items.

### Part A: Define Core (~10-15 min)
1. Domain model: `MenuItem` (id, restaurantId, name, description, price)
2. Business logic interface:
   - `MenuItem createMenuItem(MenuItem item)`
   - `MenuItem findById(int id)`
   - `List<MenuItem> findByRestaurantId(int restaurantId)`

### Part B: Inbound Components (~15 min)
1. **Inbound Port** (interface): Define REST methods
2. **Inbound Adapter** (REST Controller):
   - `@RestController` with endpoints
   - Depends on Inbound Port

### Part C: Outbound Components (~15 min)
1. **Outbound Port** (interface): Define persistence methods
2. **Outbound Adapter** (Repository):
   - Implements Outbound Port
   - Uses Spring Data JPA

### Part D: Wire Together (~15 min)
1. Implement `MenuService`:
   - Business logic (no framework dependencies)
   - Depends on Outbound Port (injected)
2. Configure Spring dependency injection

**Bonus:** Add a messaging adapter (Kafka consumer)

### Key Concepts
- **Ports** = interfaces (contracts)
- **Adapters** = implementations (technology-specific)
- **Core** = business logic (technology-agnostic)

---

## Reference Materials
- `monolithic-application/` - monolith structure
- hexagonal architecture examples in `reference-services/`
- `Microservices interactions.md` - actual implementation

---

## Learning Path

### Completed Today
- Service decomposition analysis
- Hexagonal architecture understanding
- Basic service implementation with ports and adapters

### Prepare for Day 2
**Read before next session:**
- `docs/Microservices interactions.md` - Understand message flows
- `reference-services/order-service/outbound/adapter/MessagingOutboundAdapter.java` - Event publishing
- `docs/Kafka setup.md` - Kafka basics

### If You Need More Practice
- Repeat Exercise 3 with a different service (Product Catalog)
- Study `reference-services/billing-service/` - Simpler than order-service
- Add validation logic to your Menu Service

### Struggling? Review These
- **Hexagonal architecture:** `architectural-examples/hexagonal-architecture-example/README.md`
- **Dependency injection:** Spring documentation
- **Terminology confusion:** `docs/Terminology-Guide.md`
