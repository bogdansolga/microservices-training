package net.safedata.microservices.training.customer.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CustomerCreatedEvent implements Serializable {

    private final long orderId;
    private final LocalDateTime localDateTime;

    public CustomerCreatedEvent(long orderId) {
        this.orderId = orderId;
        this.localDateTime = LocalDateTime.now();
    }

    public long getOrderId() {
        return orderId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
