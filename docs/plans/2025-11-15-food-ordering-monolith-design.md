# Food Ordering Monolith - Domain Model Design

**Date:** 2025-11-15
**Purpose:** Transform electronics store monolith into food ordering application for teaching microservices domain concepts
**Approach:** Clean domain replacement with simplified CRUD operations

---

## Overview

This design transforms the existing monolithic application from an electronics store domain (Store/Section/Product) to a food ordering domain (Customer/Restaurant/Order). The goal is to create a teaching-focused monolith that demonstrates the business domains that will later be decomposed into microservices.

**Key Principles:**
- Simplified core flow (no event-driven complexity)
- Essential CRUD operations only
- Clear domain boundaries
- Minimal complexity for teaching purposes

---

## Domain Model

### Core Entities

#### 1. Customer
The person placing food orders.

**Fields:**
- `id` (int, primary key)
- `name` (String)
- `email` (String)
- `phone` (String)
- `address` (String)

**Relationships:**
- One-to-Many with Order

---

#### 2. Restaurant
The food provider that fulfills orders.

**Fields:**
- `id` (int, primary key)
- `name` (String)
- `location` (String)
- `cuisineType` (String)

**Relationships:**
- One-to-Many with MenuItem
- One-to-Many with Order

---

#### 3. MenuItem
Food items offered by restaurants.

**Fields:**
- `id` (int, primary key)
- `name` (String)
- `description` (String)
- `price` (double)

**Relationships:**
- Many-to-One with Restaurant

---

#### 4. Order
Orders placed by customers at restaurants.

**Fields:**
- `id` (int, primary key)
- `orderDate` (LocalDateTime)
- `status` (OrderStatus enum)
- `totalAmount` (double)

**Relationships:**
- Many-to-One with Customer
- Many-to-One with Restaurant
- One-to-Many with OrderItem
- One-to-One with Payment

---

#### 5. OrderItem
Individual line items within an order.

**Fields:**
- `id` (int, primary key)
- `quantity` (int)
- `unitPrice` (double)
- `subtotal` (double)

**Relationships:**
- Many-to-One with Order
- Many-to-One with MenuItem

---

#### 6. Payment
Billing and payment tracking for orders.

**Fields:**
- `id` (int, primary key)
- `paymentMethod` (PaymentMethod enum: CARD, CASH, ONLINE)
- `paymentStatus` (PaymentStatus enum: PENDING, COMPLETED, FAILED)
- `amount` (double)
- `paymentDate` (LocalDateTime)

**Relationships:**
- One-to-One with Order

---

### Enumerations

#### OrderStatus
Tracks the lifecycle of an order:
- `CREATED` - Order placed
- `PAYED` - Payment processed
- `PROCESSED` - Restaurant prepared food
- `DELIVERED` - Order delivered to customer

#### PaymentMethod
- `CARD`
- `CASH`
- `ONLINE`

#### PaymentStatus
- `PENDING`
- `COMPLETED`
- `FAILED`

---

## Package Structure

```
net.safedata.spring.training.foodordering/
├── model/
│   ├── Customer.java
│   ├── Restaurant.java
│   ├── MenuItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Payment.java
│   ├── OrderStatus.java           (enum)
│   ├── PaymentMethod.java         (enum)
│   └── PaymentStatus.java         (enum)
├── repository/
│   ├── CustomerRepository.java
│   ├── RestaurantRepository.java
│   ├── MenuItemRepository.java
│   ├── OrderRepository.java
│   └── PaymentRepository.java
├── service/
│   ├── CustomerService.java
│   ├── RestaurantService.java
│   ├── MenuItemService.java
│   ├── OrderService.java
│   └── PaymentService.java
├── controller/
│   ├── CustomerController.java
│   ├── RestaurantController.java
│   ├── MenuItemController.java
│   └── OrderController.java
└── dto/
    ├── OrderDTO.java
    ├── CreateOrderRequest.java
    └── MessageDTO.java (reused from existing)
```

---

## Service Layer - Essential CRUD Operations

### CustomerService
- `create(Customer)` - Register new customer
- `findById(int id)` - Get customer details
- `findAll()` - List all customers
- `update(Customer)` - Update customer info
- `delete(int id)` - Remove customer

### RestaurantService
- `create(Restaurant)` - Add new restaurant
- `findById(int id)` - Get restaurant details
- `findAll()` - List all restaurants
- `update(Restaurant)` - Update restaurant info

### MenuItemService
- `create(MenuItem)` - Add menu item
- `findById(int id)` - Get menu item
- `findByRestaurantId(int restaurantId)` - Get restaurant's menu
- `update(MenuItem)` - Update menu item
- `delete(int id)` - Remove menu item

### OrderService
- `createOrder(CreateOrderRequest)` - Place new order (creates Order + OrderItems + Payment)
- `findById(int id)` - Get order details
- `findByCustomerId(int customerId)` - Get customer's orders
- `updateStatus(int orderId, OrderStatus status)` - Update order status
- `delete(int id)` - Cancel order

### PaymentService
- `findByOrderId(int orderId)` - Get payment for an order
- `updatePaymentStatus(int paymentId, PaymentStatus status)` - Update payment status

---

## Controller Layer - REST Endpoints

### CustomerController
- `GET /api/customers` - List all customers
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### RestaurantController
- `GET /api/restaurants` - List all restaurants
- `GET /api/restaurants/{id}` - Get restaurant by ID
- `POST /api/restaurants` - Create restaurant
- `PUT /api/restaurants/{id}` - Update restaurant

### MenuItemController
- `GET /api/menu-items` - List all menu items
- `GET /api/menu-items/{id}` - Get menu item by ID
- `GET /api/restaurants/{restaurantId}/menu` - Get restaurant's menu
- `POST /api/menu-items` - Create menu item
- `PUT /api/menu-items/{id}` - Update menu item
- `DELETE /api/menu-items/{id}` - Delete menu item

### OrderController
- `GET /api/orders` - List all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/customers/{customerId}/orders` - Get customer's orders
- `POST /api/orders` - Create order
- `PUT /api/orders/{id}/status` - Update order status
- `DELETE /api/orders/{id}` - Cancel order

---

## Order Creation Flow

### Request Example
```json
POST /api/orders
{
  "customerId": 1,
  "restaurantId": 2,
  "items": [
    {"menuItemId": 10, "quantity": 2},
    {"menuItemId": 15, "quantity": 1}
  ],
  "paymentMethod": "CARD"
}
```

### OrderService.createOrder() Process
1. Validate customer exists
2. Validate restaurant exists
3. Fetch MenuItem prices for each item
4. Create Order entity with status = CREATED
5. Create OrderItem entities (calculate subtotals: quantity × unitPrice)
6. Calculate Order.totalAmount (sum of all subtotals)
7. Create Payment entity with status = PENDING
8. Save Order (cascades to OrderItems and Payment)
9. Return OrderDTO

### Status Transitions (Manual API Calls)
The order lifecycle is demonstrated through manual status updates:
- `PUT /api/orders/{id}/status` with `{"status": "PAYED"}` - Payment processed
- `PUT /api/orders/{id}/status` with `{"status": "PROCESSED"}` - Food prepared
- `PUT /api/orders/{id}/status` with `{"status": "DELIVERED"}` - Order delivered

This simple approach demonstrates the lifecycle stages without event-driven complexity.

---

## Migration Plan

### Code to Remove
- All Store/Section/Product/Manager domain entities
- ProductService, SectionService
- ProductRepository, SectionRepository
- ProductController
- ProductDTO
- Store/Manager-related security and test code

### Code to Keep
- AbstractEntity base class
- All configuration classes (DataSourceConfig, SecurityConfiguration, AsyncConfig, AspectJConfig, TaskSchedulingConfig)
- AOP aspects (LoggingAspect, ProfilingAspect)
- Security infrastructure (filters, handlers, roles)
- Exception handling (ExceptionHandlers)
- MessageDTO (reusable)

### Code to Create
- All food ordering domain entities (6 entities + 3 enums)
- All repositories (5 repositories)
- All services (5 services)
- All controllers (4 controllers)
- DTOs (OrderDTO, CreateOrderRequest)

### Application Rename
- `EpicEatsApplication.java` → `FoodOrderingApplication.java`

---

## Teaching Value

This monolithic design demonstrates:

1. **Clear Domain Boundaries** - Customer, Restaurant, Order, Payment domains clearly separated
2. **Entity Relationships** - One-to-Many, Many-to-One, One-to-One relationships
3. **Business Process Flow** - Order creation with items and payment
4. **Status Lifecycle** - CREATED → PAYED → PROCESSED → DELIVERED
5. **Foundation for Microservices** - Each domain (Customer, Restaurant, Billing, Delivery) maps to future microservices

The simplified CRUD approach keeps complexity low while teaching essential domain-driven design concepts.
