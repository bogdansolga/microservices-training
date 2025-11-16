# Training Documentation - Navigation Guide

Your guide to navigating the microservices training materials.

## Getting Started

**New to the training?** Start here:
1. Read [Glossary](architecture/04-glossary.md) - Understand the vocabulary
2. Review [Service Communication](architecture/03-service-communication.md) - System overview
3. Follow the 4-day exercise sequence below

## Training Exercises (Follow in Order)

Each day builds on the previous:

1. **[Day 1 - Foundations](exercises/day-1.md)**
   - Services decomposition
   - Hexagonal Architecture introduction
   - First service implementation

2. **[Day 2 - Communication](exercises/day-2.md)**
   - REST APIs
   - Event-driven messaging
   - Kafka integration

3. **[Day 3 - Advanced Patterns](exercises/day-3.md)**
   - Saga orchestration
   - CQRS implementation
   - Distributed transactions

4. **[Day 4 - Production Readiness](exercises/day-4.md)**
   - Testing strategies
   - Resilience patterns
   - Deployment considerations

## Code Examples (Recommended Study Order)

### Start Simple
1. **[Hexagonal Architecture Example](../architectural-examples/hexagonal-architecture-example/)**
   - Simplified reference implementation
   - Perfect for learning the pattern
   - Start here before diving into more complex examples

2. **[Monolithic Application](../monolithic-application/)**
   - A simple example for the problems that the microservices architecture solves
   - Used for the services decomposition exercise

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
- Read: [Glossary](architecture/04-glossary.md)
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

| Document | What It Covers                                                                              |
|----------|---------------------------------------------------------------------------------------------|
| [01 - Architecture Overview](architecture/01-architecture-overview.md) | **Visual diagrams**: System context, hexagonal architecture, message flows, deployment view |
| [02 - Infrastructure Abstraction](architecture/02-infrastructure-abstraction.md) | **How we stay vendor-independent**: JPA for databases, Spring Cloud Stream for messaging    |
| [03 - Service Communication](architecture/03-service-communication.md) | System architecture, message flows, event details                                           |
| [04 - Glossary](architecture/04-glossary.md) | Common terms and their meanings                                                             |
| [Setup: Kafka](setup/kafka-setup.md) | Environment configuration                                                                   |

## Troubleshooting

**Can't find example code?**
→ Check the [Glossary](architecture/04-glossary.md) for naming conventions

**Kafka won't start?**
→ See [Kafka Setup](setup/kafka-setup.md) for detailed instructions

**Lost in the code?**
→ Each service has a README.md - start there

**Need simpler example?**
→ Study `hexagonal-architecture-example/` before production services

**Confused about dependencies?**
→ Review [Glossary](architecture/04-glossary.md) - Inbound vs Outbound section

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
