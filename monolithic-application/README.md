# Epic Eats Monolithic Application

## Purpose
Reference implementation demonstrating **problematic patterns** that microservices solve. Used in Day 1 Exercise 1 for service decomposition analysis.

## Architecture
Traditional 3-Tier Layered Architecture:
- **Controller Layer** - HTTP endpoints
- **Service Layer** - Business logic (tightly coupled)
- **Repository Layer** - Database access

## The Problems (Why We Need Microservices)

### 1. Tight Coupling
**See:** `OrderService.java` - Direct dependencies on multiple services
```java
private final CustomerService customerService;
private final RestaurantService restaurantService;
private final MenuItemService menuItemService;
```
**Impact:** Cannot deploy Order changes without testing ALL dependencies

### 2. Shared Database
**See:** All entities in `model/` package share same database
**Impact:** Schema changes affect multiple teams; scaling bottlenecks

### 3. Transaction Boundaries
**See:** `OrderService.createOrder()` - Single transaction spans multiple bounded contexts
**Impact:** Cannot isolate failures; all-or-nothing deployments

### 4. Technology Lock-in
**Impact:** Entire app must use same tech stack; cannot use best tool for each job

## Service Decomposition Exercise

Identify these bounded contexts:
1. **Order Management** - Order, OrderItem entities
2. **Billing** - Payment entity
3. **Customer Management** - Customer entity
4. **Restaurant Management** - Restaurant, MenuItem entities
5. **Delivery** - (to be implemented)

## Compare With Microservices

After decomposition analysis, see how this was split:
- `reference-services/order-service/` - Owns Order aggregate
- `reference-services/billing-service/` - Owns Payment aggregate
- `reference-services/customer-service/` - Owns Customer aggregate
- `reference-services/restaurant-service/` - Owns Restaurant aggregate
- `reference-services/delivery-service/` - Owns Delivery aggregate

Key improvements:
- Independent deployment
- Separate databases
- Async communication (events)
- Technology diversity possible

## Running

```bash
cd monolithic-application
mvn spring-boot:run
```

Access: http://localhost:8080

## Related Training Materials
- **Day 1 Exercise 1:** Service decomposition analysis
- **docs/03-service-communication.md:** Comparing monolith vs microservices
