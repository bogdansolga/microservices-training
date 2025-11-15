# Training Documentation - Navigation Guide

Your guide to navigating the microservices training materials.

## Getting Started

**New to the training?** Start here:
1. Read [Terminology Guide](Terminology-Guide.md) - Understand the vocabulary
2. Review [Microservices Interactions](Microservices%20interactions.md) - System overview
3. Follow the 4-day exercise sequence below

## Training Exercises (Follow in Order)

Each day builds on the previous:

1. **[Day 1 - Foundations](Day-1-Hands-On-Exercises-Participant.md)**
   - Service decomposition
   - Hexagonal architecture
   - First service implementation

2. **[Day 2 - Communication](Day-2-Hands-On-Exercises-Participant.md)**
   - REST APIs
   - Event-driven messaging
   - Kafka integration

3. **[Day 3 - Advanced Patterns](Day-3-Hands-On-Exercises-Participant.md)**
   - Saga orchestration
   - CQRS implementation
   - Distributed transactions

4. **[Day 4 - Production Readiness](Day-4-Hands-On-Exercises-Participant.md)**
   - Testing strategies
   - Resilience patterns
   - Deployment considerations

## Code Examples (Recommended Study Order)

### Start Simple
1. **[Hexagonal Architecture Example](../architectural-examples/hexagonal-architecture-example/)**
   - Simplified reference implementation
   - Perfect for learning the pattern
   - Start here before diving into production code

2. **[Monolithic Application](../monolithic-application/)**
   - Shows problems microservices solve
   - Used for decomposition exercise

### Production Examples
Study these during training (each has detailed README):

| Service | Purpose | Key Learning |
|---------|---------|--------------|
| [Order Service](../reference-services/order-service/) | Order management | Complete hexagonal architecture |
| [Billing Service](../reference-services/billing-service/) | Payment processing | Simpler example, good for beginners |
| [Customer Service](../reference-services/customer-service/) | Customer profiles | Saga orchestration |
| [Restaurant Service](../reference-services/restaurant-service/) | Menu management | Domain modeling |
| [Delivery Service](../reference-services/delivery-service/) | Delivery tracking | Event choreography |

## Study Path by Training Day

### Day 1 Preparation
- Read: [Terminology Guide](Terminology-Guide.md)
- Study: `hexagonal-architecture-example/`
- Review: `monolithic-application/`

### During Day 1
- Focus: Hexagonal architecture pattern
- Code: `hexagonal-architecture-example/` + `order-service/`
- Exercise: Implement Menu Service

### During Day 2
- Focus: REST APIs and Kafka
- Code: `order-service/` + `billing-service/`
- Exercise: Event-driven communication

### During Day 3
- Focus: Sagas and CQRS
- Code: `customer-service/` (orchestration examples)
- Exercise: Distributed transaction patterns

### During Day 4
- Focus: Testing and resilience
- Code: All services (testing patterns)
- Exercise: Production readiness

## Documentation

| Document | What It Covers |
|----------|----------------|
| [Architecture Overview](Architecture-Overview.md) | **Visual diagrams**: System context, hexagonal pattern, message flows, deployment view |
| [Microservices Interactions](Microservices%20interactions.md) | System architecture, message flows, event details |
| [Kafka Setup](Kafka%20setup.md) | Environment configuration |
| [Terminology Guide](Terminology-Guide.md) | Common terms and their meanings |

## Troubleshooting

**Can't find example code?**
→ Check the [Terminology Guide](Terminology-Guide.md) for naming conventions

**Kafka won't start?**
→ See [Kafka Setup](Kafka%20setup.md) for detailed instructions

**Lost in the code?**
→ Each service has a README.md - start there

**Need simpler example?**
→ Study `hexagonal-architecture-example/` before production services

**Confused about dependencies?**
→ Review [Terminology Guide](Terminology-Guide.md) - Inbound vs Outbound section

## Quick Reference

**All services run on different ports:**
- Order Service: `http://localhost:8081`
- Billing Service: `http://localhost:8082`
- Customer Service: `http://localhost:8083`
- Restaurant Service: `http://localhost:8084`
- Delivery Service: `http://localhost:8085`

**Kafka:**
- Bootstrap servers: `localhost:9092`
- Topics defined in each service's `application.yml`
