package net.safedata.microservices.training.order.message.event;

import net.safedata.microservices.training.order.marker.message.Publisher;
import net.safedata.microservices.training.order.marker.message.Service;
import net.safedata.microservices.training.order.marker.message.Subscriber;

@Publisher(Service.ORDER_SERVICE)
@Subscriber({
        Service.CUSTOMER_SERVICE,
        Service.BILLING_SERVICE
})
public class OrderCreatedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderCreated";

    private final long customerId;
    private final long orderId;

    public OrderCreatedEvent(final long messageId, final long eventId, final long customerId, final long orderId) {
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
}
