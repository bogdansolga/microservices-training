# Food Ordering Monolith Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Transform the electronics store monolith into a food ordering application with Customer, Restaurant, MenuItem, Order, OrderItem, and Payment domains.

**Architecture:** Clean domain replacement removing all Store/Section/Product code. Simple CRUD operations with JPA repositories, service layer, and REST controllers. Order creation orchestrates multiple entities in a single transaction.

**Tech Stack:** Spring Boot, JPA/Hibernate, PostgreSQL, Spring Web MVC, Jackson

---

## Task 1: Remove Old Domain Code

**Files:**
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Product.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Section.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Store.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Manager.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/StoreManager.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/StoreManagerPK.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/ElectronicsStore.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/service/ProductService.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/service/SectionService.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/repository/ProductRepository.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/repository/SectionRepository.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/controller/ProductController.java`
- Delete: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/dto/ProductDTO.java`

**Step 1: Remove old domain entities**

```bash
cd monolithic-application/src/main/java/net/safedata/spring/training/complete/project
rm -f model/Product.java model/Section.java model/Store.java model/Manager.java model/StoreManager.java model/StoreManagerPK.java model/ElectronicsStore.java
```

**Step 2: Remove old services and repositories**

```bash
rm -f service/ProductService.java service/SectionService.java
rm -f repository/ProductRepository.java repository/SectionRepository.java
```

**Step 3: Remove old controller and DTOs**

```bash
rm -f controller/ProductController.java
rm -f dto/ProductDTO.java
```

**Step 4: Commit removal**

```bash
git add -A
git commit -m "chore: remove old electronics store domain code

Removed Product, Section, Store, Manager entities and related
services, repositories, controllers to prepare for food ordering domain.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 2: Create Enum Types

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/OrderStatus.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/PaymentMethod.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/PaymentStatus.java`

**Step 1: Create OrderStatus enum**

```java
package net.safedata.spring.training.complete.project.model;

public enum OrderStatus {
    CREATED,
    PAYED,
    PROCESSED,
    DELIVERED
}
```

**Step 2: Create PaymentMethod enum**

```java
package net.safedata.spring.training.complete.project.model;

public enum PaymentMethod {
    CARD,
    CASH,
    ONLINE
}
```

**Step 3: Create PaymentStatus enum**

```java
package net.safedata.spring.training.complete.project.model;

public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}
```

**Step 4: Commit enums**

```bash
git add model/OrderStatus.java model/PaymentMethod.java model/PaymentStatus.java
git commit -m "feat: add order and payment status enums

Added OrderStatus (CREATED, PAYED, PROCESSED, DELIVERED),
PaymentMethod (CARD, CASH, ONLINE), and PaymentStatus
(PENDING, COMPLETED, FAILED) enums.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 3: Create Customer Entity

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Customer.java`

**Step 1: Create Customer entity**

```java
package net.safedata.spring.training.complete.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Customer")
public class Customer extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "customer_sequence_generator")
    @SequenceGenerator(name = "customer_sequence_generator", sequenceName = "customer_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "address", length = 200)
    private String address;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    protected Customer() {}

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

**Step 2: Commit Customer entity**

```bash
git add model/Customer.java
git commit -m "feat: add Customer entity

Customer entity with name, email, phone, address and
one-to-many relationship with Order.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 4: Create Restaurant and MenuItem Entities

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Restaurant.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/MenuItem.java`

**Step 1: Create Restaurant entity**

```java
package net.safedata.spring.training.complete.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Restaurant")
public class Restaurant extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "restaurant_sequence_generator")
    @SequenceGenerator(name = "restaurant_sequence_generator", sequenceName = "restaurant_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Column(name = "cuisine_type", length = 50)
    private String cuisineType;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant")
    private Set<Order> orders;

    protected Restaurant() {}

    public Restaurant(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant that = (Restaurant) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                '}';
    }
}
```

**Step 2: Create MenuItem entity**

```java
package net.safedata.spring.training.complete.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "MenuItem")
public class MenuItem extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "menu_item_sequence_generator")
    @SequenceGenerator(name = "menu_item_sequence_generator", sequenceName = "menu_item_sequence", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    protected MenuItem() {}

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.id && Objects.equals(name, menuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
```

**Step 3: Commit Restaurant and MenuItem**

```bash
git add model/Restaurant.java model/MenuItem.java
git commit -m "feat: add Restaurant and MenuItem entities

Restaurant with name, location, cuisineType and one-to-many
with MenuItem. MenuItem with name, description, price and
many-to-one with Restaurant.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 5: Create Order, OrderItem, and Payment Entities

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Order.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/OrderItem.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/model/Payment.java`

**Step 1: Create Order entity**

```java
package net.safedata.spring.training.complete.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "FoodOrder")
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "order_sequence_generator")
    @SequenceGenerator(name = "order_sequence_generator", sequenceName = "order_sequence", allocationSize = 1)
    private int id;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    protected Order() {}

    public Order(Customer customer, Restaurant restaurant) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
```

**Step 2: Create OrderItem entity**

```java
package net.safedata.spring.training.complete.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "OrderItem")
public class OrderItem extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "order_item_sequence_generator")
    @SequenceGenerator(name = "order_item_sequence_generator", sequenceName = "order_item_sequence", allocationSize = 1)
    private int id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    protected OrderItem() {}

    public OrderItem(Order order, MenuItem menuItem, int quantity) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.unitPrice = menuItem.getPrice();
        this.subtotal = quantity * unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subtotal = quantity * unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                '}';
    }
}
```

**Step 3: Create Payment entity**

```java
package net.safedata.spring.training.complete.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Payment")
public class Payment extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "payment_sequence_generator")
    @SequenceGenerator(name = "payment_sequence_generator", sequenceName = "payment_sequence", allocationSize = 1)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    protected Payment() {}

    public Payment(Order order, PaymentMethod paymentMethod, double amount) {
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        if (paymentStatus == PaymentStatus.COMPLETED && this.paymentDate == null) {
            this.paymentDate = LocalDateTime.now();
        }
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return id == payment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", amount=" + amount +
                '}';
    }
}
```

**Step 4: Commit Order, OrderItem, Payment**

```bash
git add model/Order.java model/OrderItem.java model/Payment.java
git commit -m "feat: add Order, OrderItem, and Payment entities

Order with status lifecycle, many-to-one with Customer and Restaurant,
one-to-many with OrderItem, one-to-one with Payment. OrderItem tracks
quantity, unitPrice, subtotal. Payment tracks method, status, amount.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 6: Create JPA Repositories

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/repository/CustomerRepository.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/repository/RestaurantRepository.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/repository/MenuItemRepository.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/repository/OrderRepository.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/repository/PaymentRepository.java`

**Step 1: Create CustomerRepository**

```java
package net.safedata.spring.training.complete.project.repository;

import net.safedata.spring.training.complete.project.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
```

**Step 2: Create RestaurantRepository**

```java
package net.safedata.spring.training.complete.project.repository;

import net.safedata.spring.training.complete.project.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
```

**Step 3: Create MenuItemRepository**

```java
package net.safedata.spring.training.complete.project.repository;

import net.safedata.spring.training.complete.project.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByRestaurantId(int restaurantId);
}
```

**Step 4: Create OrderRepository**

```java
package net.safedata.spring.training.complete.project.repository;

import net.safedata.spring.training.complete.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(int customerId);
}
```

**Step 5: Create PaymentRepository**

```java
package net.safedata.spring.training.complete.project.repository;

import net.safedata.spring.training.complete.project.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByOrderId(int orderId);
}
```

**Step 6: Commit repositories**

```bash
git add repository/CustomerRepository.java repository/RestaurantRepository.java repository/MenuItemRepository.java repository/OrderRepository.java repository/PaymentRepository.java
git commit -m "feat: add JPA repositories for all entities

Added repositories for Customer, Restaurant, MenuItem, Order, Payment
with custom query methods for finding menu items by restaurant and
orders by customer.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 7: Create DTOs

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/dto/CreateOrderRequest.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/dto/OrderItemRequest.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/dto/OrderDTO.java`

**Step 1: Create OrderItemRequest DTO**

```java
package net.safedata.spring.training.complete.project.dto;

public class OrderItemRequest {
    private int menuItemId;
    private int quantity;

    public OrderItemRequest() {}

    public OrderItemRequest(int menuItemId, int quantity) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
```

**Step 2: Create CreateOrderRequest DTO**

```java
package net.safedata.spring.training.complete.project.dto;

import net.safedata.spring.training.complete.project.model.PaymentMethod;
import java.util.List;

public class CreateOrderRequest {
    private int customerId;
    private int restaurantId;
    private List<OrderItemRequest> items;
    private PaymentMethod paymentMethod;

    public CreateOrderRequest() {}

    public CreateOrderRequest(int customerId, int restaurantId, List<OrderItemRequest> items, PaymentMethod paymentMethod) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.items = items;
        this.paymentMethod = paymentMethod;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
```

**Step 3: Create OrderDTO**

```java
package net.safedata.spring.training.complete.project.dto;

import net.safedata.spring.training.complete.project.model.OrderStatus;
import net.safedata.spring.training.complete.project.model.PaymentMethod;
import net.safedata.spring.training.complete.project.model.PaymentStatus;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private int customerId;
    private String customerName;
    private int restaurantId;
    private String restaurantName;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private double totalAmount;
    private List<OrderItemDTO> items;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    public static class OrderItemDTO {
        private int menuItemId;
        private String menuItemName;
        private int quantity;
        private double unitPrice;
        private double subtotal;

        public OrderItemDTO() {}

        public OrderItemDTO(int menuItemId, String menuItemName, int quantity, double unitPrice, double subtotal) {
            this.menuItemId = menuItemId;
            this.menuItemName = menuItemName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.subtotal = subtotal;
        }

        public int getMenuItemId() {
            return menuItemId;
        }

        public void setMenuItemId(int menuItemId) {
            this.menuItemId = menuItemId;
        }

        public String getMenuItemName() {
            return menuItemName;
        }

        public void setMenuItemName(String menuItemName) {
            this.menuItemName = menuItemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
```

**Step 4: Commit DTOs**

```bash
git add dto/CreateOrderRequest.java dto/OrderItemRequest.java dto/OrderDTO.java
git commit -m "feat: add DTOs for order creation and response

CreateOrderRequest for placing orders, OrderItemRequest for
line items, and OrderDTO for returning order details with
customer, restaurant, items, and payment info.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 8: Create Service Layer - Part 1 (Customer, Restaurant, MenuItem)

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/service/CustomerService.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/service/RestaurantService.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/service/MenuItemService.java`

**Step 1: Create CustomerService**

```java
package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.model.Customer;
import net.safedata.spring.training.complete.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer findById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer update(Customer customer) {
        if (!customerRepository.existsById(customer.getId())) {
            throw new RuntimeException("Customer not found with id: " + customer.getId());
        }
        return customerRepository.save(customer);
    }

    @Transactional
    public void delete(int id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
```

**Step 2: Create RestaurantService**

```java
package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.model.Restaurant;
import net.safedata.spring.training.complete.project.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public Restaurant findById(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public Restaurant update(Restaurant restaurant) {
        if (!restaurantRepository.existsById(restaurant.getId())) {
            throw new RuntimeException("Restaurant not found with id: " + restaurant.getId());
        }
        return restaurantRepository.save(restaurant);
    }
}
```

**Step 3: Create MenuItemService**

```java
package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.model.MenuItem;
import net.safedata.spring.training.complete.project.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public MenuItem create(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Transactional(readOnly = true)
    public MenuItem findById(int id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<MenuItem> findByRestaurantId(int restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Transactional(readOnly = true)
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Transactional
    public MenuItem update(MenuItem menuItem) {
        if (!menuItemRepository.existsById(menuItem.getId())) {
            throw new RuntimeException("MenuItem not found with id: " + menuItem.getId());
        }
        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public void delete(int id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("MenuItem not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}
```

**Step 4: Commit services**

```bash
git add service/CustomerService.java service/RestaurantService.java service/MenuItemService.java
git commit -m "feat: add Customer, Restaurant, MenuItem services

Basic CRUD operations for Customer, Restaurant, and MenuItem
with transaction management and error handling.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 9: Create Service Layer - Part 2 (Order, Payment)

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/service/OrderService.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/service/PaymentService.java`

**Step 1: Create OrderService**

```java
package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.dto.CreateOrderRequest;
import net.safedata.spring.training.complete.project.dto.OrderDTO;
import net.safedata.spring.training.complete.project.dto.OrderItemRequest;
import net.safedata.spring.training.complete.project.model.Customer;
import net.safedata.spring.training.complete.project.model.MenuItem;
import net.safedata.spring.training.complete.project.model.Order;
import net.safedata.spring.training.complete.project.model.OrderItem;
import net.safedata.spring.training.complete.project.model.OrderStatus;
import net.safedata.spring.training.complete.project.model.Payment;
import net.safedata.spring.training.complete.project.model.Restaurant;
import net.safedata.spring.training.complete.project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                       CustomerService customerService,
                       RestaurantService restaurantService,
                       MenuItemService menuItemService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        // Validate customer and restaurant
        Customer customer = customerService.findById(request.getCustomerId());
        Restaurant restaurant = restaurantService.findById(request.getRestaurantId());

        // Create order
        Order order = new Order(customer, restaurant);

        // Create order items
        Set<OrderItem> orderItems = new HashSet<>();
        double totalAmount = 0.0;

        for (OrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemService.findById(itemRequest.getMenuItemId());
            OrderItem orderItem = new OrderItem(order, menuItem, itemRequest.getQuantity());
            orderItems.add(orderItem);
            totalAmount += orderItem.getSubtotal();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        // Create payment
        Payment payment = new Payment(order, request.getPaymentMethod(), totalAmount);
        order.setPayment(payment);

        // Save order (cascades to items and payment)
        Order savedOrder = orderRepository.save(order);

        return toDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return toDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO updateStatus(int orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return toDTO(updatedOrder);
    }

    @Transactional
    public void delete(int id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setRestaurantName(order.getRestaurant().getName());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderDTO.OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> new OrderDTO.OrderItemDTO(
                        item.getMenuItem().getId(),
                        item.getMenuItem().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);

        if (order.getPayment() != null) {
            dto.setPaymentMethod(order.getPayment().getPaymentMethod());
            dto.setPaymentStatus(order.getPayment().getPaymentStatus());
        }

        return dto;
    }
}
```

**Step 2: Create PaymentService**

```java
package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.model.Payment;
import net.safedata.spring.training.complete.project.model.PaymentStatus;
import net.safedata.spring.training.complete.project.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional(readOnly = true)
    public Payment findByOrderId(int orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order id: " + orderId));
    }

    @Transactional
    public Payment updatePaymentStatus(int paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }
}
```

**Step 3: Commit Order and Payment services**

```bash
git add service/OrderService.java service/PaymentService.java
git commit -m "feat: add Order and Payment services

OrderService handles order creation with items and payment,
status updates, and DTO conversion. PaymentService handles
payment status updates.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 10: Create Controllers - Part 1 (Customer, Restaurant, MenuItem)

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/controller/CustomerController.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/controller/RestaurantController.java`
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/controller/MenuItemController.java`

**Step 1: Create CustomerController**

```java
package net.safedata.spring.training.complete.project.controller;

import net.safedata.spring.training.complete.project.model.Customer;
import net.safedata.spring.training.complete.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer created = customerService.create(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        customer.setId(id);
        return ResponseEntity.ok(customerService.update(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Step 2: Create RestaurantController**

```java
package net.safedata.spring.training.complete.project.controller;

import net.safedata.spring.training.complete.project.model.Restaurant;
import net.safedata.spring.training.complete.project.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable int id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.create(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable int id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        return ResponseEntity.ok(restaurantService.update(restaurant));
    }
}
```

**Step 3: Create MenuItemController**

```java
package net.safedata.spring.training.complete.project.controller;

import net.safedata.spring.training.complete.project.model.MenuItem;
import net.safedata.spring.training.complete.project.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable int id) {
        return ResponseEntity.ok(menuItemService.findById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable int restaurantId) {
        return ResponseEntity.ok(menuItemService.findByRestaurantId(restaurantId));
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem created = menuItemService.create(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable int id, @RequestBody MenuItem menuItem) {
        menuItem.setId(id);
        return ResponseEntity.ok(menuItemService.update(menuItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable int id) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Step 4: Commit controllers**

```bash
git add controller/CustomerController.java controller/RestaurantController.java controller/MenuItemController.java
git commit -m "feat: add Customer, Restaurant, MenuItem controllers

REST endpoints for CRUD operations on Customer, Restaurant,
and MenuItem with proper HTTP status codes.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 11: Create Order Controller

**Files:**
- Create: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/controller/OrderController.java`

**Step 1: Create OrderController**

```java
package net.safedata.spring.training.complete.project.controller;

import net.safedata.spring.training.complete.project.dto.CreateOrderRequest;
import net.safedata.spring.training.complete.project.dto.OrderDTO;
import net.safedata.spring.training.complete.project.model.OrderStatus;
import net.safedata.spring.training.complete.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable int customerId) {
        return ResponseEntity.ok(orderService.findByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        OrderDTO created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable int id,
            @RequestBody Map<String, String> statusUpdate) {
        OrderStatus status = OrderStatus.valueOf(statusUpdate.get("status"));
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Step 2: Commit OrderController**

```bash
git add controller/OrderController.java
git commit -m "feat: add Order controller

REST endpoints for order creation, retrieval by customer,
status updates, and order cancellation.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 12: Rename Application Entry Point

**Files:**
- Modify: `monolithic-application/src/main/java/net/safedata/spring/training/complete/project/EpicEatsApplication.java`

**Step 1: Rename EpicEatsApplication to FoodOrderingApplication**

```java
package net.safedata.spring.training.complete.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodOrderingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingApplication.class, args);
    }
}
```

**Step 2: Delete old file and commit**

```bash
rm EpicEatsApplication.java
git add FoodOrderingApplication.java
git add -u  # Stage deletion
git commit -m "refactor: rename application to FoodOrderingApplication

Renamed main application class from EpicEatsApplication to
FoodOrderingApplication to reflect the new domain.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Task 13: Update Application Properties (if needed)

**Files:**
- Check: `monolithic-application/src/main/resources/application.properties` or `application.yml`

**Step 1: Review application properties**

Check if database configuration needs updates. The existing configuration should work, but verify table creation strategy is appropriate:

```properties
spring.jpa.hibernate.ddl-auto=update
```

**Step 2: If changes needed, commit**

If you made changes:
```bash
git add src/main/resources/application.properties
git commit -m "config: update database configuration for food ordering domain

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

If no changes needed, skip this commit.

---

## Task 14: Build and Test

**Step 1: Clean and compile**

```bash
cd monolithic-application
mvn clean compile
```

Expected: BUILD SUCCESS

**Step 2: Run the application**

```bash
mvn spring-boot:run
```

Expected: Application starts without errors. Check for:
- No compilation errors
- Hibernate creates tables: Customer, Restaurant, MenuItem, FoodOrder, OrderItem, Payment
- Application runs on port 8080 (or configured port)

**Step 3: Test basic endpoints**

Create a customer:
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","phone":"555-1234","address":"123 Main St"}'
```

Expected: Customer created with HTTP 201

Create a restaurant:
```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Content-Type: application/json" \
  -d '{"name":"Pizza Palace","location":"Downtown","cuisineType":"Italian"}'
```

Expected: Restaurant created with HTTP 201

**Step 4: Stop application and commit verification**

```bash
git add -A
git commit -m "verify: build and basic endpoint testing successful

Application compiles, runs, and accepts basic REST requests
for Customer and Restaurant creation.

🤖 Generated with [Claude Code](https://claude.com/claude-code)

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

## Summary

This implementation plan transforms the electronics store monolith into a food ordering application with:

- **6 entities**: Customer, Restaurant, MenuItem, Order, OrderItem, Payment
- **3 enums**: OrderStatus, PaymentMethod, PaymentStatus
- **5 repositories**: Standard JPA repositories with custom queries
- **5 services**: CRUD operations with transaction management
- **4 controllers**: REST endpoints for all entities
- **Clean domain separation**: Removed all old Store/Product code

**Key Features:**
- Order creation orchestrates Customer, Restaurant, MenuItem, OrderItem, and Payment in a single transaction
- Order status lifecycle (CREATED → PAYED → PROCESSED → DELIVERED) via manual API calls
- Simple CRUD operations ideal for teaching microservices domain concepts

**Next Steps:**
- Add validation annotations
- Add comprehensive error handling
- Add unit and integration tests
- Add API documentation (Swagger/OpenAPI)
- Add sample data initialization
