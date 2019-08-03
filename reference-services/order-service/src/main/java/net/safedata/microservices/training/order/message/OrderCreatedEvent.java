package net.safedata.microservices.training.order.message;

import net.safedata.microservices.training.order.message.subscriber.Service;
import net.safedata.microservices.training.order.message.subscriber.Subscriber;

@Subscriber(Service.ORDER_SERVICE)
public class OrderCreatedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderCreated";

    private final long customerId;
    private final long orderId;

    public OrderCreatedEvent(final long messageId, final long eventId, final long customerId, final long orderId) {
        super(messageId, eventId);
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
