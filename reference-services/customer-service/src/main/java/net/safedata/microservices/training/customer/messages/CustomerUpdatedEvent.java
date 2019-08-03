package net.safedata.microservices.training.customer.messages;

import net.safedata.microservices.training.customer.messages.subscriber.Service;
import net.safedata.microservices.training.customer.messages.subscriber.Subscriber;

import java.time.LocalDateTime;

@Subscriber(Service.CUSTOMER_SERVICE)
public class CustomerUpdatedEvent extends AbstractDomainEvent {

    private static final String NAME = "CustomerUpdated";

    private final long customerId;
    private final LocalDateTime localDateTime;

    public CustomerUpdatedEvent(final long messageId, final long eventId, final long customerId) {
        super(messageId, eventId);
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
