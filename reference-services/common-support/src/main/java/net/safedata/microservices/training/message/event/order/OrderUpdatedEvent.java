package net.safedata.microservices.training.message.event.order;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.ORDER_SERVICE,
        subscribers = {
                Service.CUSTOMER_SERVICE,
                Service.ORDER_SERVICE
        },
        channel = Channel.ORDER_CREATED
)
public class OrderUpdatedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderCreated";

    private final long customerId;
    private final long orderId;

    public OrderUpdatedEvent(final long messageId, final long eventId, final long customerId, final long orderId) {
        super(messageId, eventId);
        this.customerId = customerId;
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "customerId: " + customerId +
                ", orderId: " + orderId +
                '}';
    }
}
