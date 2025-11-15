# Day 4 - Hands-On Exercises
**Focus: Testing, Resilience & Production Readiness**

---

## Exercise 1: Resilience Patterns (2-3 hours)

### Objective
Add fault-tolerance patterns to handle failures gracefully.

### Part A: Circuit Breaker (60 min)

**Scenario:** Restaurant Service becomes overloaded during peak hours.

**Setup (Resilience4j):**
```xml
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot3</artifactId>
</dependency>
```

**Configure:**
```yaml
resilience4j.circuitbreaker.instances.restaurantService:
  slidingWindowSize: 10
  minimumNumberOfCalls: 5
  failureRateThreshold: 50
  waitDurationInOpenState: 10s
```

**Apply:**
```java
@CircuitBreaker(name = "restaurantService", fallbackMethod = "fallbackProcessOrder")
public void processOrder(ProcessOrderCommand cmd) {
    // Call Restaurant Service
}

private void fallbackProcessOrder(ProcessOrderCommand cmd, Exception e) {
    // Fallback: queue for later, notify customer
}
```

**Test:**
- Closed state: Normal operation
- Open state: Stop service → circuit opens after 5 failures
- Half-open: Restart service → circuit attempts recovery
- Fallback: Verify fallback called when circuit open

### Part B: Retry Pattern (30 min)

**Scenario:** Payment gateway has transient failures.

**Configure:**
```yaml
resilience4j.retry.instances.paymentGateway:
  maxAttempts: 3
  waitDuration: 1s
  exponentialBackoffMultiplier: 2
```

**Apply:**
```java
@Retry(name = "paymentGateway", fallbackMethod = "fallbackCharge")
public PaymentResult chargeCustomer(ChargeRequest request) {
    return paymentGateway.charge(request);
}
```

**Test:**
- Transient failure: Succeeds on 3rd attempt
- Exponential backoff: 1s, 2s, 4s delays
- Non-retryable: Payment declined = no retry

### Part C: Rate Limiting (30 min)

**Scenario:** Protect Order API from excessive requests.

**Configure:**
```yaml
resilience4j.ratelimiter.instances.orderApi:
  limitForPeriod: 10
  limitRefreshPeriod: 1s
```

**Apply:**
```java
@RateLimiter(name = "orderApi")
@PostMapping("/order")
public ResponseEntity<String> createOrder(@RequestBody OrderDTO order)
```

**Test:**
- Send 15 requests/second
- First 10 succeed, next 5 get HTTP 429
- After 1 second, limit resets

### Part D: Bulkhead (30 min)

**Scenario:** Isolate thread pools (slow DB queries don't block API).

**Configure:**
```yaml
resilience4j.bulkhead.instances.databaseOperations:
  maxConcurrentCalls: 5
  maxWaitDuration: 1s
```

**Apply:**
```java
@Bulkhead(name = "databaseOperations")
public List<Order> findAllOrders() {
    return orderRepository.findAll();
}
```

**Test:**
- 10 concurrent requests
- Only 5 execute concurrently, others wait/fail

### Success Criteria
✅ Circuit breaker opens after threshold
✅ Fallbacks execute when circuit open
✅ Retry handles transient failures
✅ Rate limiter protects API
✅ Bulkhead isolates resources

---

## Exercise 2: Testing Strategy (2-3 hours)

### Objective
Implement multi-level testing: unit, integration, component, contract, E2E.

### Part A: Unit Tests (30 min)

**Test business logic in isolation:**
```java
@Test
void shouldCalculateOrderTotalCorrectly() {
    Order order = new Order();
    order.addItem(new OrderItem("Pizza", 2, 12.50));
    order.addItem(new OrderItem("Soda", 1, 2.50));

    assertEquals(27.50, order.calculateTotal(), 0.01);
}
```

**With mocks:**
```java
@Mock
private PersistenceOutboundPort persistencePort;

@InjectMocks
private OrderService orderService;

@Test
void shouldPublishEventAfterCreatingOrder() {
    when(persistencePort.save(any())).thenReturn(savedOrder);

    orderService.createOrder(orderDTO);

    verify(messagingPort).publishOrderCreatedEvent(any());
}
```

### Part B: Integration Tests (45 min)

**Test with real dependencies (Testcontainers):**
```java
@SpringBootTest
@Testcontainers
class OrderRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:18-alpine").withDatabaseName("test");;

    @Test
    void shouldPersistAndRetrieveOrder() {
        Order saved = orderRepository.save(order);
        Optional<Order> retrieved = orderRepository.findById(saved.getId());

        assertTrue(retrieved.isPresent());
    }
}
```

### Part C: Component Tests (45 min)

**Test entire service with mocked externals:**
```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
class OrderServiceComponentTest {

    @Container
    static PostgreSQLContainer<?> postgres = /* ... */;

    @Container
    static KafkaContainer kafka = /* ... */;

    @MockBean
    private ProductCatalogClient productCatalogClient;

    @Test
    void shouldCreateOrderEndToEnd() {
        // Mock external dependencies
        when(productCatalogClient.getProduct(1))
            .thenReturn(new ProductDTO(1, "Pizza", 12.50));

        // Call API
        ResponseEntity<String> response = restTemplate.postForEntity(
            "/order", request, String.class);

        // Verify: response, database, events
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

### Part D: Contract Tests (45 min)

**Consumer defines contract (Pact):**
```java
@Pact(consumer = "OrderService")
public RequestResponsePact getProductContract(PactDslWithProvider builder) {
    return builder
        .given("product 1 exists")
        .uponReceiving("request for product 1")
        .path("/products/1")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(/* expected JSON */)
        .toPact();
}
```

**Provider verifies contract:**
```java
@Provider("ProductCatalogService")
@SpringBootTest
class ProductCatalogContractVerificationTest {

    @State("product 1 exists")
    void productExists() {
        productRepository.save(new Product(1, "Pizza", 12.50));
    }
}
```

### Part E: End-to-End Tests (45 min)

**Test complete workflow:**
```java
@Test
void shouldProcessOrderFromCreationToDelivery() {
    // Create order
    ResponseEntity<String> response = createOrder(request);
    int orderId = extractOrderId(response);

    // Wait for billing
    await().atMost(5, SECONDS).until(() ->
        getOrderStatus(orderId).equals("PAYED")
    );

    // Wait for processing
    await().atMost(10, SECONDS).until(() ->
        getOrderStatus(orderId).equals("PROCESSED")
    );

    // Wait for delivery
    await().atMost(10, SECONDS).until(() ->
        getOrderStatus(orderId).equals("DELIVERED")
    );

    // Verify final state
    assertEquals(OrderStatus.DELIVERED, getOrder(orderId).getStatus());
}
```

### Success Criteria
✅ Unit tests cover business logic (>80%)
✅ Integration tests verify DB + messaging
✅ Component tests validate service behavior
✅ Contract tests ensure API compatibility
✅ E2E tests validate workflows
✅ Tests run in CI/CD < 5 minutes

---

## Exercise 3: Observability (Optional, 60 min)

### Objective
Add logging, metrics, tracing for production visibility.

### Part A: Correlation ID (20 min)

**Add to all logs:**
```java
@Component
public class CorrelationIdFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
```

### Part B: Metrics (20 min)

**Custom metrics:**
```java
@Service
public class OrderService {
    private final Counter orderCounter;
    private final Timer orderProcessingTimer;

    public OrderService(MeterRegistry registry) {
        this.orderCounter = registry.counter("orders.created");
        this.orderProcessingTimer = registry.timer("orders.processing.time");
    }

    public void createOrder(OrderDTO dto) {
        orderProcessingTimer.record(() -> {
            // Create order
            orderCounter.increment();
        });
    }
}
```

**Expose:** `/actuator/prometheus`

### Part C: Distributed Tracing (20 min)

**Configure Zipkin:**
```yaml
management.tracing.sampling.probability: 1.0
management.zipkin.tracing.endpoint: http://localhost:9411/api/v2/spans
```

**Start Zipkin:** `docker run -d -p 9411:9411 openzipkin/zipkin`

**View traces:** Create order → See complete flow across services in Zipkin UI

### Success Criteria
✅ Logs include correlation ID
✅ Custom metrics exposed
✅ Traces visible in Zipkin

---

## Exercise 4: Production Readiness Checklist (60 min)

### Objective
Audit services for production deployment.

### Checklist

**Configuration:**
- ✅ Externalized configuration
- ✅ Environment-specific properties (dev, staging, prod)
- ✅ Secrets management (not hardcoded)

**Security:**
- ✅ Authentication & Authorization
- ✅ HTTPS enabled
- ✅ API rate limiting
- ✅ Input validation

**Resilience:**
- ✅ Circuit breakers on external calls
- ✅ Retry logic with backoff
- ✅ Timeouts configured
- ✅ Fallbacks implemented

**Observability:**
- ✅ Structured logging + correlation IDs
- ✅ Metrics exposed (Prometheus)
- ✅ Distributed tracing
- ✅ Health checks (/actuator/health)

**Testing:**
- ✅ Unit tests (>80% coverage)
- ✅ Integration tests
- ✅ Contract tests
- ✅ E2E smoke tests

**Deployment:**
- ✅ CI/CD pipeline
- ✅ Blue-green or canary strategy
- ✅ Database migration strategy
- ✅ Rollback plan

### Group Activity
- Audit one service against checklist
- Present findings: What's missing?
- Prioritize improvements

---

## Training Wrap-Up & Retrospective (60 min)

### What We Learned (20 min)
Round-robin: "The most valuable thing I learned was..."

### What Worked Well (15 min)
- Which exercises were most effective?
- What helped understanding?

### What Could Be Improved (15 min)
- What was confusing?
- Additional topics needed?
- Pacing feedback?

### Action Items (10 min)
- What will you apply in projects?
- What to study further?
- Resources for continued learning?

---

## Reference Materials
- `Kafka setup.md` - Kafka installation (for integration tests)
- Resilience4j: https://resilience4j.readme.io/
- Testcontainers: https://testcontainers.com/
- Spring Boot Testing: https://spring.io/guides/gs/testing-web/
