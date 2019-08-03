package net.safedata.microservices.training.customer.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CustomerUpdatedEvent implements Serializable {

    private final long customerId;
    private final LocalDateTime localDateTime;

    public CustomerUpdatedEvent(long customerId) {
        this.customerId = customerId;
        this.localDateTime = LocalDateTime.now();
    }

    public long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
