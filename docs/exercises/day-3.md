# Day 3 - Hands-On Exercises
**Focus: Data Management, CQRS & Sagas**

---

## Exercise 1: Understanding Order Processing Saga (45 min)

### Objective
Analyze the existing order processing saga to understand orchestration patterns.

### Part A: Saga Flow Analysis (20 min)

**Review the complete flow** (see `Microservices interactions.md`):

```
1. Order Service ← CreateOrderCommand
   → OrderCreatedEvent + ChargeOrderCommand

2. Billing Service ← ChargeOrderCommand
   → OrderChargedEvent

3. Order Service + Customer Service ← OrderChargedEvent
   → Order status: PAYED
   → Customer Service publishes ProcessOrderCommand

4. Restaurant Service ← ProcessOrderCommand
   → OrderProcessedEvent + DeliverOrderCommand

5. Delivery Service ← DeliverOrderCommand
   → OrderDeliveredEvent

6. Order Service ← OrderDeliveredEvent
   → Order status: DELIVERED (final)
```

**Answer:**
1. Which services orchestrate workflow steps?
2. Where do services react independently to events?
3. What happens if billing fails?

### Part B: Event Tracing Exercise (15 min)

**Scenario:** Order $45.50 → Payment succeeds → Restaurant closed

**Trace:**
1. What events are published?
2. Which services react to each?
3. Final order status?
4. What compensating actions should occur?

### Part C: Discussion (10 min)

**Compare Patterns:**

| Pattern | Orchestration | Choreography |
|---------|--------------|--------------|
| Coordinator | Central orchestrator | Distributed |
| Coupling | Services know orchestrator | Services only know events |
| Visibility | Easy to see workflow | Workflow emerges |
| Use When | Complex workflows | Simple reactions |

---

## Exercise 2: Implement Update Order Saga (3-4 hours)

### Objective
Design and implement saga for updating orders with compensating transactions.

### Business Context
Customer modifies order before processing: change quantities, add/remove items.

### Part A: Saga Design (45 min)

**Define Commands & Events:**
- `UpdateOrderCommand`
- `RefundDifferenceCommand` / `ChargeAdditionalCommand`
- `OrderUpdatedEvent` / `OrderUpdateFailedEvent`
- `PaymentAdjustedEvent`

**Saga Steps:**
```
1. Validate order can be updated (not PROCESSED)
2. Calculate price difference
3. Adjust payment (charge more or refund)
4. Update order items
5. Notify restaurant
6. Publish OrderUpdatedEvent

Failures → Compensate → Rollback
```

**State Machine:**
```
PENDING → VALIDATING → ADJUSTING_PAYMENT → UPDATING → COMPLETED
              ↓              ↓                  ↓
          REJECTED    PAYMENT_FAILED     UPDATE_FAILED
                           ↓                  ↓
                      COMPENSATING ← ← ← ← ← ┘
                           ↓
                      ROLLED_BACK
```

### Part B: Order Service Implementation (60 min)

**API Endpoint:**
```java
@PostMapping("/{orderId}/update")
public ResponseEntity<String> updateOrder(
    @PathVariable int orderId,
    @RequestBody UpdateOrderRequest request)
```

**Saga Orchestrator:**
1. Validate order can be updated
2. Calculate price difference
3. Publish charge/refund command if needed
4. Wait for PaymentAdjustedEvent
5. Update order items
6. Handle failures with compensation

**State Management:**
- Track saga state in database
- Handle idempotency (same request twice = same result)

### Part C: Billing Service Implementation (45 min)

**Handle Commands:**
```java
@Consumer
public void onChargeAdditionalCommand(ChargeAdditionalCommand cmd) {
    try {
        paymentGateway.charge(cmd.getAmount());
        publishPaymentAdjustedEvent(cmd.getOrderId());
    } catch (PaymentException e) {
        publishPaymentFailedEvent(cmd.getOrderId());
    }
}
```

**Implement Idempotency:**
- Store processed command IDs
- Check before executing
- Return same result if duplicate

### Part D: Restaurant Service (30 min)

**Handle Update Notification:**
```java
@Consumer
public void onUpdateRestaurantOrderCommand(UpdateRestaurantOrderCommand cmd) {
    // Validate new items available
    // Update kitchen display
    // Publish RestaurantOrderUpdatedEvent
}
```

### Part E: Testing (45 min)

**Test Cases:**
1. **Happy Path**: Add item ($30 → $45) → $15 charged → Order updated
2. **Payment Failure**: Additional charge fails → Order reverts → Failure event
3. **Restaurant Rejection**: Unavailable item → Payment refunded → Order unchanged
4. **Idempotency**: Same request twice → Only processed once

### Part F: Group Presentations (30 min)
- Present design decisions
- Failure scenarios handled
- Challenges encountered

### Success Criteria
✅ Saga designed with all steps mapped
✅ Happy path working end-to-end
✅ ≥2 compensating transactions implemented
✅ Idempotency handled
✅ Rollback on failure demonstrated

---

## Exercise 3: Cancel Order Saga (Alternative)

### Objective
Implement order cancellation with compensation.

### Scenario
Customer cancels before delivery: refund payment, cancel preparation, release driver.

**Saga Steps:**
1. Validate order can be cancelled (not DELIVERED)
2. Refund payment
3. Cancel restaurant preparation
4. Release delivery driver (if assigned)
5. Update order status to CANCELLED

**Handle Edge Cases:**
- Already delivered → Cannot cancel
- Partial refund if food prepared
- Cancellation fees

### Success Criteria
✅ Cancellation flow implemented
✅ Refunds processed correctly
✅ State validations prevent invalid cancellations
✅ Compensating transactions work

---

## Exercise 4: CQRS - Order History Query (Optional, 60 min)

### Objective
Create separate read model optimized for queries.

### Business Context
"My Orders" queries are slow on write-optimized database.

### Part A: Design Read Model (15 min)

**Denormalized Schema:**
```sql
CREATE TABLE order_history_view (
    order_id INT PRIMARY KEY,
    customer_id INT,
    customer_name VARCHAR(255),
    restaurant_name VARCHAR(255),
    order_date TIMESTAMP,
    status VARCHAR(50),
    total_amount DECIMAL(10,2),
    items_summary TEXT
);

CREATE INDEX idx_customer_date ON order_history_view(customer_id, order_date);
```

**Data Sources:**
- `OrderCreatedEvent` → Initial record
- `OrderChargedEvent` → Add payment
- Status events → Update status

### Part B: Projection Service (30 min)

**Projector:**
```java
@Service
public class OrderHistoryProjector {

    @Consumer
    public void onOrderCreated(OrderCreatedEvent event) {
        OrderHistoryView view = new OrderHistoryView();
        view.setOrderId(event.getOrderId());
        view.setCustomerId(event.getCustomerId());
        view.setStatus("CREATED");
        repository.save(view);
    }

    @Consumer
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        OrderHistoryView view = repository.findById(event.getOrderId());
        view.setStatus(event.getNewStatus());
        repository.save(view);
    }
}
```

**Query API:**
```java
@GetMapping("/customer/{customerId}")
public List<OrderHistoryView> getCustomerOrders(
    @PathVariable int customerId,
    @RequestParam String status,
    @RequestParam LocalDate fromDate)
```

### Part C: Testing (15 min)

1. Create orders via write model
2. Query via read model
3. Verify eventual consistency (1s delay acceptable)
4. Compare performance: JOIN vs. denormalized table

### Success Criteria
✅ Read model populated from events
✅ Query API returns denormalized data
✅ Eventual consistency demonstrated
✅ Performance improvement measurable

---

## Reference Materials
- `Kafka setup.md` - Kafka installation and topic creation
- `Microservices interactions.md` - complete saga flows
- `reference-services/order-service/` - saga implementation
- `reference-services/customer-service/` - orchestration examples

---

## Learning Path

### Completed Today
- Saga pattern for distributed transactions
- CQRS implementation (command and query separation)
- Event sourcing concepts
- Compensation logic for rollbacks

### Prepare for Day 4
**Read before next session:**
- Research: Testing strategies for microservices
- Research: Circuit breaker pattern
- Review all services you've built for testing gaps

### If You Need More Practice
- Add compensation logic to your Menu Service
- Implement CQRS for customer queries
- Create a read model with event sourcing
- Add timeout handling to saga steps

### Struggling? Review These
- **Saga patterns:** `docs/Microservices interactions.md` - Study the order creation flow
- **CQRS basics:** Research Martin Fowler's CQRS article
- **Event flows:** Trace a complete saga through all services with logging
