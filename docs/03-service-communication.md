# Microservices Messaging Architecture Report

## **Technology Stack**
- **Messaging Framework**: [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream) - Broker-agnostic messaging abstraction
- **Current Binder**: Kafka (via `spring-cloud-stream-binder-kafka`)
- **Broker**: Apache Kafka (localhost:9092)
- **Pattern**: Event-Driven Architecture with CQRS

### Broker Flexibility

All services use **Spring Cloud Stream**, which provides a broker-agnostic abstraction layer. This means:
- **Easy switching**: Replace Kafka with RabbitMQ, Azure Service Bus, AWS Kinesis, Google Pub/Sub, or other supported brokers
- **No code changes**: Simply update dependencies and configuration files
- **Same API**: `StreamBridge` for publishing, `@Bean Consumer<>` for consuming works across all binders

**To switch brokers**: Replace the `spring-cloud-stream-binder-kafka` dependency with your preferred binder (e.g., `spring-cloud-stream-binder-rabbit`) and update broker connection properties in `application.yml`.

Learn more: [Spring Cloud Stream Documentation](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/)

## **Message Types**
- **Commands**: Instructions to perform actions (charge_order, ship_order, process_order, deliver_order)
- **Events**: Notifications of completed actions (order_created, order_charged, order_shipped, etc.)
- **Queries**: Information requests (find_order)

---

## **Complete Message Flow**

```
┌─────────────────────────────────────────────────────────────┐
│ ORCHESTRATION: Order Processing Lifecycle                   │
└─────────────────────────────────────────────────────────────┘

1. ORDER SERVICE (8081) ← CreateOrderCommand
   ├─→ Publishes: OrderCreatedEvent → order_created
   └─→ Publishes: ChargeOrderCommand → charge_order

2. BILLING SERVICE (8082) ← ChargeOrderCommand
   └─→ Publishes: OrderChargedEvent → order_charged

3. ORDER SERVICE + CUSTOMER SERVICE ← OrderChargedEvent
   ├─→ Order Service: Updates status to PAYED
   └─→ Customer Service: Publishes ProcessOrderCommand → process_order

4. RESTAURANT SERVICE (8084) ← ProcessOrderCommand
   ├─→ Publishes: OrderProcessedEvent → order_processed
   └─→ Publishes: DeliverOrderCommand → deliver_order

5. ORDER SERVICE + CUSTOMER SERVICE ← OrderProcessedEvent
   ├─→ Order Service: Updates status to PROCESSED
   └─→ Customer Service: Tracks preparation progress

6. DELIVERY SERVICE (8085) ← DeliverOrderCommand
   └─→ Publishes: OrderDeliveredEvent → order_delivered

7. ORDER SERVICE + CUSTOMER SERVICE ← OrderDeliveredEvent
   ├─→ Order Service: Updates status to DELIVERED (final!)
   └─→ Customer Service: Sends delivery confirmation
```

---

## **Service Responsibilities**

| Service | Port | Publishes | Consumes | Role |
|---------|------|-----------|----------|------|
| **Order** | 8081 | OrderCreatedEvent, ChargeOrderCommand | CreateOrderCommand, CustomerCreatedEvent, CustomerUpdatedEvent, OrderChargedEvent, OrderNotChargedEvent, OrderProcessedEvent, OrderDeliveredEvent | **Orchestrator & Lifecycle Tracker** |
| **Billing** | 8082 | OrderChargedEvent, OrderNotChargedEvent | ChargeOrderCommand | Payment processor |
| **Customer** | 8083 | CustomerCreatedEvent, CustomerUpdatedEvent, ProcessOrderCommand | OrderCreatedEvent, OrderChargedEvent, OrderNotChargedEvent, OrderProcessedEvent, OrderDeliveredEvent | Customer aggregator + Restaurant orchestrator |
| **Restaurant** | 8084 | OrderProcessedEvent, DeliverOrderCommand | ProcessOrderCommand | Food preparation + Delivery initiator |
| **Delivery** | 8085 | OrderDeliveredEvent | DeliverOrderCommand | Delivery execution |

---

## **Key Patterns**

**1. Dual Orchestration:**
- **Order Service**: Orchestrates billing & shipping
- **Customer Service**: Orchestrates restaurant processing & delivery

**2. Event Broadcasting:**
- Multiple services subscribe to the same events (e.g., OrderChargedEvent → Order + Customer)

**3. Self-Subscription:**
- Order Service consumes its own OrderCreatedEvent for internal processing

**4. Command-Event Chain:**
- Commands trigger actions → Events notify completion → Next command initiated

---

## **Implementation Details**

All messaging uses **Spring Cloud Stream** APIs for broker independence.

**Publishing Mechanism (Broker-Agnostic):**
```java
@Async
StreamBridge.send(bindingName, message)
```
Works with any Spring Cloud Stream binder (Kafka, RabbitMQ, Azure Service Bus, etc.)

**Consuming Mechanism (Broker-Agnostic):**
```java
@Bean
public Consumer<Message<CommandType>> handlerName() { ... }
```
Consumer functions work identically across all supported brokers

**Configuration Pattern:**
```yaml
# Functional programming model (broker-independent)
spring.cloud.function.definition: consumer1;consumer2;producer1

# Binding configuration (broker-specific properties in application.yml)
spring.cloud.stream.bindings:
  consumer1-in-0:
    destination: topic_name  # or queue name, depending on broker
  producer1-out-0:
    destination: topic_name
```

**Kafka-Specific Configuration (Current Setup):**
```yaml
spring.cloud.stream.kafka.binder.brokers: localhost:9092
```

To switch to RabbitMQ, replace the Kafka dependency with `spring-cloud-stream-binder-rabbit` and update configuration to:
```yaml
spring.rabbitmq.host: localhost
spring.rabbitmq.port: 5672
```

## **Order Lifecycle Status Tracking**

The Order Service maintains complete lifecycle tracking through these statuses:

| Status | Triggered By | Description |
|--------|-------------|-------------|
| `CREATED` | Order creation | Initial state when order is created |
| `IN_PROCESSING` | Business logic | Order is being processed |
| `PAYED` | OrderChargedEvent | Payment successfully processed |
| `PAYMENT_FAILED` | OrderNotChargedEvent | Payment processing failed |
| `PROCESSED` | OrderProcessedEvent | Restaurant completed food preparation |
| `DELIVERED` | OrderDeliveredEvent | Final delivery completed |

---

## **Message Flow Summary Table**

| Message | Publisher → Consumers | Topic |
|---------|----------------------|-------|
| OrderCreatedEvent | Order → Customer, Order | order_created |
| ChargeOrderCommand | Order → Billing | charge_order |
| OrderChargedEvent | Billing → Order, Customer | order_charged |
| OrderNotChargedEvent | Billing → Order | order_not_charged |
| ProcessOrderCommand | Customer → Restaurant | process_order |
| OrderProcessedEvent | Restaurant → Order, Customer | order_processed |
| DeliverOrderCommand | Restaurant → Delivery | deliver_order |
| OrderDeliveredEvent | Delivery → Order, Customer | order_delivered |
| CustomerCreatedEvent | Customer → Order | customer_created |
| CustomerUpdatedEvent | Customer → Order | customer_updated |

---

**Architecture Quality**: Event-Driven Architecture (EDA) with clear separation of concerns.
All services use:
- **Hexagonal architecture** with inbound/outbound adapters
- **Spring Cloud Stream** for broker-agnostic messaging (currently Kafka, easily switchable)
- **Common message definitions** centralized in `common-support` module
- **Complete message flows** with handlers for the entire order lifecycle

The broker-agnostic design using Spring Cloud Stream allows switching from Kafka to RabbitMQ, Azure Service Bus, AWS Kinesis, Google Pub/Sub, or other supported brokers without changing business logic code.
