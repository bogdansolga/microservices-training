package net.safedata.microservices.training.order.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {


    @Column(name = "customer_id", length = 10)
    private long customerId;

    @Column(name = "total", length = 10)
    private double total;

    @Enumerated
    @Column(name = "status", length = 20)
    private OrderStatus status;

    @Column(name = "creation_date", length = 10)
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private Set<OrderItem> orderItems;

    public Order(final long customerId, final double total) {
        this.customerId = customerId;
        this.total = total;
        this.creationDate = LocalDateTime.now();
    }

    // required by JPA
    protected Order() {
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                customerId == order.customerId &&
                Double.compare(order.total, total) == 0 &&
                Objects.equals(creationDate, order.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, total, creationDate);
    }
}
