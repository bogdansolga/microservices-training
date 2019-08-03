package net.safedata.microservices.training.customer.messages.event;

import net.safedata.microservices.training.customer.marker.message.Publisher;
import net.safedata.microservices.training.customer.marker.message.Service;
import net.safedata.microservices.training.customer.marker.message.Subscriber;

import java.time.LocalDateTime;

@Publisher(Service.ORDER_SERVICE)
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
