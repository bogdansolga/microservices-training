package net.safedata.microservices.training.message.command.order;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractCommand;

import java.util.Objects;

@MessageDetails(
        publisher = Service.ORDER_SERVICE,
        subscribers = Service.BILLING_SERVICE,
        channel = Channel.SHIP_ORDER
)
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
