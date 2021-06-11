package com.microservices.workshop.common.messaging.message;

import java.io.Serializable;

public class ChargeCustomer implements Serializable {

    private final long customerId;
    private final long orderId;
    private final String correlationId;

    public ChargeCustomer(long customerId, long orderId, String correlationId) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.correlationId = correlationId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
