package net.safedata.microservices.training.message.event.customer;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.CUSTOMER_SERVICE,
        subscribers = Service.ORDER_SERVICE,
        channel = Channel.CUSTOMER_UPDATED
)
public class CustomerUpdatedEvent extends AbstractDomainEvent {

    private static final String NAME = "CustomerUpdated";

    private final long customerId;

    public CustomerUpdatedEvent(final long messageId, final long eventId, final long customerId) {
        super(messageId, eventId);
        this.customerId = customerId;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public long getCustomerId() {
        return customerId;
    }
}
