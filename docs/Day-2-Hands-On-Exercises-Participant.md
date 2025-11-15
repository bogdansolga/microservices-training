# Day 2 - Hands-On Exercises
**Focus: Inter-Process Communication (Synchronous & Asynchronous)**

---

## Exercise 1: Synchronous Communication - Product Catalog Service (~1-2 hours)

### Objective
Build a Product Catalog Service and establish REST communication with the Order Service.

### Part A: Service Design (30 min)

**Define API Contract (API-First):**
- `GET /products/{id}` → ProductDTO
- `GET /products/restaurant/{restaurantId}` → List<ProductDTO>
- `POST /products` → Create new product
- `PUT /products/{id}/availability` → Update availability

**Identify:**
- Data ownership: Product, Category entities
- Boundaries: Product CRUD, pricing (IN scope) vs. Order management (OUT of scope)

### Part B: Implementation (60 min)

**Create Product Catalog Service using hexagonal architecture:**

1. **Domain Layer**
   - `Product` entity (id, restaurantId, name, description, price, available)
   - `ProductService` interface

2. **Inbound Adapter (REST API)**
   - REST controller with defined endpoints
   - Inbound port interface

3. **Outbound Adapter (Persistence)**
   - Persistence port interface
   - Repository implementation (Spring Data JPA)

4. **Business Rules**
   - Product names unique per restaurant
   - Price must be positive
   - Cannot delete products in active orders

### Part C: Service-to-Service Communication (45 min)

**Integrate with Order Service:**

1. Create REST client in Order Service:
   - Outbound adapter: `ProductCatalogClient`
   - Use `RestTemplate` or `WebClient`
   - Call: `http://localhost:8087/products/{id}`

2. Modify Order Service:
   - Validate product availability before creating order
   - Handle failure: What if Product Catalog is down?

3. Test Integration:
   - Start both services
   - Create product via Catalog API
   - Create order referencing that product

### Success Criteria
✅ Product Catalog running on port 8087
✅ REST API responds to requests
✅ Order Service calls Product Catalog via HTTP
✅ End-to-end: Create product → Create order

---

## Exercise 2: Asynchronous Messaging - Order Created Event (2-3 hours)

### Objective
Implement event-driven communication using Kafka.

### Business Context
When order created, notify:
- Billing Service (charge customer)
- Customer Service (update history)
- Restaurant Service (prepare food)

### Part A: Event Design (20 min)

**Define Event Schema:**
```json
{
  "eventType": "OrderCreatedEvent",
  "orderId": 123,
  "customerId": 456,
  "restaurantId": 789,
  "totalAmount": 45.50,
  "items": [...]
}
```

**Characteristics:**
- Type: Event (past tense - "OrderCreated")
- Ownership: Order Service publishes, others subscribe

### Part B: Kafka Setup (15 min)

**Follow the setup instructions in `Kafka setup.md`**

Quick steps:
1. Start Kafka (see `Kafka setup.md` for details)
2. Create topic: `kafka-topics.sh --create --topic order_created --bootstrap-server localhost:9092`
3. Verify: `kafka-topics.sh --list --bootstrap-server localhost:9092`

### Part C: Publisher - Order Service (45 min)

**Add dependency:** Spring Cloud Stream + Kafka binder

**Create Messaging Adapter:**
```java
@OutboundAdapter
public class MessagingOutboundAdapter {
    private final StreamBridge streamBridge;

    @Async
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        streamBridge.send("order_created", event);
    }
}
```

**Configure:**
```yaml
spring.cloud.stream.bindings:
  order_created-out-0:
    destination: order_created
```

**Modify OrderService:** Publish event after creating order

### Part D: Consumer - Billing Service (45 min)

**Create Messaging Adapter:**
```java
@InboundAdapter
public class InboundMessagingAdapter {

    @Bean
    public Consumer<Message<OrderCreatedEvent>> orderCreatedConsumer() {
        return message -> {
            OrderCreatedEvent event = message.getPayload();
            billingService.chargeOrder(event);
        };
    }
}
```

**Configure:**
```yaml
spring.cloud.function.definition: orderCreatedConsumer
spring.cloud.stream.bindings:
  orderCreatedConsumer-in-0:
    destination: order_created
    group: billing-service
```

### Part E: Testing (30 min)

**Verify Event Flow:**
1. Create order via Order Service API
2. Check Kafka: `kafka-console-consumer --topic order_created`
3. Check Billing Service logs: Event consumed?
4. Verify billing record in database

**Test Failure:**
- Stop Billing Service, create order → Event queues
- Restart Billing → Processes queued events

### Part F: Discussion (15 min)

**Compare:**

| Aspect | Synchronous (REST) | Asynchronous (Events) |
|--------|-------------------|---------------------|
| Coupling | Tight - caller waits | Loose - fire & forget |
| Availability | Both must be up | Producer independent |
| Performance | Blocking | Non-blocking |
| Use Case | Queries, immediate response | Notifications, eventual consistency |

### Success Criteria
✅ Order Service publishes events to Kafka
✅ Billing Service consumes and processes
✅ Events visible in Kafka topic
✅ Works when consumer temporarily down

---

## Reference Materials
- `Kafka setup.md` - Kafka installation and topic creation
- `reference-services/order-service/` - hexagonal structure
- `reference-services/billing-service/` - messaging examples
- `Microservices interactions.md` - complete event flows

---

## Learning Path

### Completed Today
- REST API implementation with hexagonal architecture
- Event-driven communication with Kafka
- Producer and consumer patterns
- Asynchronous messaging resilience

### Prepare for Day 3
**Read before next session:**
- `docs/Microservices interactions.md` - Focus on saga patterns
- `reference-services/customer-service/` - Orchestration examples
- Research: What is the Saga pattern and why is it needed?

### If You Need More Practice
- Add error handling to your Kafka consumers
- Implement dead letter queue pattern
- Add REST endpoints to your Menu Service from Day 1
- Create a new event type and publish/consume it

### Struggling? Review These
- **Kafka basics:** `docs/Kafka setup.md`
- **Event patterns:** Study `MessagingOutboundAdapter.java` in billing-service
- **REST APIs:** `RestInboundAdapter.java` examples in all services
