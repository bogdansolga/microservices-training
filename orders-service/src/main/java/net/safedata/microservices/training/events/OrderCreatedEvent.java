package net.safedata.microservices.training.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderCreatedEvent implements Serializable {

    private final long orderId;
    private final LocalDateTime localDateTime;

    public OrderCreatedEvent(long orderId) {
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
