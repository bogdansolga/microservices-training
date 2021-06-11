package com.microservices.workshop.orders.domain.entity.write;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Set;

@Entity
public class CreateOrderSaga implements Serializable {

    @Id
    private long id;

    @OneToOne
    private Order order;

    @OneToMany
    private Set<CreateOrderSagaStep> sagaSteps;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Set<CreateOrderSagaStep> getSagaSteps() {
        return sagaSteps;
    }

    public void setSagaSteps(Set<CreateOrderSagaStep> sagaSteps) {
        this.sagaSteps = sagaSteps;
    }
}
