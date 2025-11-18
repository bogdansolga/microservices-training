package net.safedata.microservices.training.customer.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {

    private String name;
    private String email;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer() {
        this.createdAt = LocalDateTime.now();
    }

    public Customer(String name, String email, String address) {
        this();
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
