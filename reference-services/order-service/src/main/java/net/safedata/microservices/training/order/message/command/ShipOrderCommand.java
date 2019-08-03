package net.safedata.microservices.training.order.message.command;

import net.safedata.microservices.training.order.marker.message.Publisher;
import net.safedata.microservices.training.order.marker.message.Service;
import net.safedata.microservices.training.order.marker.message.Subscriber;

import java.util.Objects;

@Publisher(Service.ORDER_SERVICE)
@Subscriber(Service.BILLING_SERVICE)
public class ShipOrderCommand extends AbstractCommand {

    private static final String NAME = "ShipOrder";

    private final long customerId;
    private final long orderId;

    public ShipOrderCommand(long messageId, long customerId, long orderId) {
        super(messageId);
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
        ShipOrderCommand that = (ShipOrderCommand) o;
        return customerId == that.customerId &&
                orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderId);
    }
}
