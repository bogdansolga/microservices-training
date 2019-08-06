package net.safedata.microservices.training.message.event.order;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractDomainEvent;

import java.util.Objects;

@MessageDetails(
        publisher = Service.BILLING_SERVICE,
        subscribers = {
                Service.BILLING_SERVICE,
                Service.ORDER_SERVICE
        },
        channel = Channel.ORDER_CHARGED
)
public class OrderChargedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderCharged";

    private final long customerId;
    private final long orderId;

    public OrderChargedEvent(final long messageId, final long eventId, final long customerId, long orderId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderChargedEvent that = (OrderChargedEvent) o;
        return customerId == that.customerId &&
                orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderId);
    }
}
