package com.microservices.workshop.orders.domain.entity.write;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class CreateOrderSagaStep implements Serializable {

    @Id
    private long id;

    @Column(name = "step_name", length = 10)
    private String stepName;

    @Column(name = "step_status", length = 10)
    private StepStatus stepStatus;

    @ManyToOne
    private CreateOrderSaga saga;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CreateOrderSaga getSaga() {
        return saga;
    }

    public void setSaga(CreateOrderSaga saga) {
        this.saga = saga;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public StepStatus getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(StepStatus stepStatus) {
        this.stepStatus = stepStatus;
    }
}
