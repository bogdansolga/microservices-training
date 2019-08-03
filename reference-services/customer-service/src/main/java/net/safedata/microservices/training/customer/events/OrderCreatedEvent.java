package net.safedata.microservices.training.customer.events;

import java.time.LocalDateTime;

public class OrderCreatedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderCreated";

    private final long customerId;
    private final long orderId;
    private final LocalDateTime localDateTime;

    public OrderCreatedEvent(long eventId, final long customerId, long orderId) {
        super(eventId);
        this.customerId = customerId;
        this.orderId = orderId;
        this.localDateTime = LocalDateTime.now();
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
