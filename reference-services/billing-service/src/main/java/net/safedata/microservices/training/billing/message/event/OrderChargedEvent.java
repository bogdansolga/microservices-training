package net.safedata.microservices.training.billing.message.event;

import net.safedata.microservices.training.billing.marker.message.Service;
import net.safedata.microservices.training.billing.marker.message.Subscriber;

import java.util.Objects;

@Subscriber({
        Service.CUSTOMER_SERVICE,
        Service.ORDER_SERVICE
})
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
