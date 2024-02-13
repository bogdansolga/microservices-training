package net.safedata.microservices.training.message.event.order;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.BILLING_SERVICE,
        subscribers = Service.RESTAURANT_SERVICE,
        channel = Channel.ORDER_PROCESSED
)
public class OrderProcessedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderProcessed";

    private final long customerId;
    private final long orderId;

    public OrderProcessedEvent(final long messageId, final long eventId, final long customerId, final long orderId) {
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
