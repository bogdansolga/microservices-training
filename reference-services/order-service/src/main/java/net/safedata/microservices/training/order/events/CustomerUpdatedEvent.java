package net.safedata.microservices.training.order.events;

import java.time.LocalDateTime;

public class CustomerUpdatedEvent extends AbstractDomainEvent {

    private static final String NAME = "CustomerUpdated";

    private final long customerId;
    private final LocalDateTime localDateTime;

    public CustomerUpdatedEvent(final long eventId, final long customerId) {
        super(eventId);
        this.customerId = customerId;
        this.localDateTime = LocalDateTime.now();
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
