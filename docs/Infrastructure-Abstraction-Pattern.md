# Infrastructure Abstraction Pattern

How we keep our code independent from specific databases and message brokers.

## The Problem

If you write code directly against specific technologies (PostgreSQL, Kafka, etc.), you're **locked in**:
- Hard to switch technologies later
- Difficult to test without running real infrastructure
- Business logic mixed with technical details

## The Solution: Abstraction Layers

We use **standard interfaces** that hide the implementation details.

### Pattern 1: Database Abstraction

```
Your Code
    ↓
JPA (Java Persistence API) ← Standard interface
    ↓
Hibernate ← Implementation (pluggable)
    ↓
PostgreSQL / MySQL / H2 / Oracle ← Actual database (switchable)
```

**What you write:**
```java
@Entity
public class Order {
    @Id
    private Long id;
}

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}
```

**What you get:**
- ✅ Works with PostgreSQL, MySQL, H2, Oracle - just change configuration
- ✅ No database-specific SQL in your code
- ✅ Easy to test with in-memory H2 database

**How to switch databases:**
Change only the dependency and configuration:
```xml
<!-- From PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

<!-- To MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

### Pattern 2: Messaging Abstraction

```
Your Code
    ↓
Spring Cloud Stream ← Standard interface
    ↓
Kafka Binder / RabbitMQ Binder / Azure Binder ← Implementation (pluggable)
    ↓
Kafka / RabbitMQ / Azure Service Bus ← Actual broker (switchable)
```

**What you write:**
```java
// Publishing messages
@Autowired
private StreamBridge streamBridge;

public void publishOrder(Order order) {
    streamBridge.send("orders-out-0", order);
}

// Consuming messages
@Bean
public Consumer<Order> handleOrder() {
    return order -> {
        // Process order
    };
}
```

**What you get:**
- ✅ Works with Kafka, RabbitMQ, Azure Service Bus, AWS Kinesis - just change configuration
- ✅ No broker-specific code
- ✅ Same code, different brokers

**How to switch brokers:**
Change only the dependency:
```xml
<!-- From Kafka -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>

<!-- To RabbitMQ -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
</dependency>
```

## Why This Matters

### For Development
- **Start simple:** Use H2 database and Kafka locally
- **Easy testing:** Swap for test doubles/in-memory alternatives

### For Production
- **Flexibility:** Choose best technology for your needs
- **Cloud migration:** Switch from self-hosted Kafka to AWS Kinesis without code changes
- **Cost optimization:** Move to cheaper alternatives without rewriting

### For Hexagonal Architecture
These abstractions are the **"Ports"** in our hexagonal architecture:
- **Persistence Port** → JPA Repository → Database Adapter
- **Messaging Port** → Spring Cloud Stream → Message Broker Adapter

## Key Principle

> **"Program to an interface, not an implementation"**

Your business logic should only know about:
- ✅ **JPA** interfaces (not Hibernate)
- ✅ **Spring Cloud Stream** APIs (not Kafka)
- ✅ **Your domain model** (Orders, Customers)

It should NOT know about:
- ❌ Specific database vendors
- ❌ Specific message brokers
- ❌ Infrastructure details

## Real Example from Our Code

```java
// ❌ BAD - Tightly coupled to Kafka
import org.apache.kafka.clients.producer.KafkaProducer;
KafkaProducer producer = new KafkaProducer(...);
producer.send(new ProducerRecord("orders", order));

// ✅ GOOD - Abstracted, broker-agnostic
@Autowired
private StreamBridge streamBridge;
streamBridge.send("orders-out-0", order);
```

```java
// ❌ BAD - Tightly coupled to PostgreSQL
import org.postgresql.Driver;
// Direct PostgreSQL-specific code

// ✅ GOOD - Abstracted, database-agnostic
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Works with any JPA-compatible database
}
```

## Summary

| Layer | Abstraction | Current Implementation | Easy to Switch? |
|-------|-------------|----------------------|----------------|
| **Database** | JPA | Hibernate + H2/PostgreSQL | ✅ Yes (change dependency) |
| **Messaging** | Spring Cloud Stream | Kafka Binder + Kafka | ✅ Yes (change dependency) |

Both patterns give you **vendor independence** and **flexibility** without sacrificing functionality.

Learn more:
- [Spring Cloud Stream Documentation](https://spring.io/projects/spring-cloud-stream)
- [JPA Specification](https://jakarta.ee/specifications/persistence/)
