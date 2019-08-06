package net.safedata.microservices.training.message.event.order;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractDomainEvent;

@MessageDetails(
        publisher = Service.SHIPPING_SERVICE,
        subscribers = {
                Service.BILLING_SERVICE,
                Service.CUSTOMER_SERVICE,
                Service.ORDER_SERVICE
        },
        channel = Channel.ORDER_SHIPPED
)
public class OrderShippedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderShipped";

    private final long customerId;
    private final long orderId;

    public OrderShippedEvent(final long messageId, final long eventId, final long customerId, long orderId) {
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
