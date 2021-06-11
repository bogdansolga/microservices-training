package com.microservices.workshop.orders.domain.entity.write;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "customer_id", length = 5)
    private int customerId;

    @Column(name = "amount", length = 5)
    private double amount;

    @Column(name = "correlation_id", length = 20)
    private String correlationId;

    @Column(name = "status", length = 10)
    private Status status;

    @OneToOne
    private CreateOrderSaga createOrderSaga;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
