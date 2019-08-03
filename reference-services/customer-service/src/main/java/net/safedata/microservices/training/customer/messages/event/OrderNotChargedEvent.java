package net.safedata.microservices.training.customer.messages.event;

import net.safedata.microservices.training.customer.marker.message.Publisher;
import net.safedata.microservices.training.customer.marker.message.Service;
import net.safedata.microservices.training.customer.marker.message.Subscriber;

import java.util.Objects;

@Publisher(Service.BILLING_SERVICE)
@Subscriber({
        Service.CUSTOMER_SERVICE,
        Service.ORDER_SERVICE
})
public class OrderNotChargedEvent extends AbstractDomainEvent {

    private static final String NAME = "OrderCharged";

    private final long customerId;
    private final long orderId;
    private final String reason;

    public OrderNotChargedEvent(final long messageId, final long eventId, final long customerId, long orderId,
                                final String reason) {
        super(messageId, eventId);
        this.customerId = customerId;
        this.orderId = orderId;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNotChargedEvent that = (OrderNotChargedEvent) o;
        return customerId == that.customerId &&
                orderId == that.orderId &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderId, reason);
    }
}
