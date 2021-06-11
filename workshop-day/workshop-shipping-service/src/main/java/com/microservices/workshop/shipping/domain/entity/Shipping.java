package com.microservices.workshop.shipping.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Shipping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "customer_id", length = 5)
    private int customerId;

    @Column(name = "amount", length = 5)
    private double amount;

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
}
