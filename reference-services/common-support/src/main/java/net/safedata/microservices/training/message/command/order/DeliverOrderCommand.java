package net.safedata.microservices.training.message.command.order;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractCommand;

import java.util.Objects;

@MessageDetails(
        publisher = Service.RESTAURANT_SERVICE,
        subscribers = Service.DELIVERY_SERVICE,
        channel = Channel.DELIVER_ORDER
)
public class DeliverOrderCommand extends AbstractCommand {

    private static final String NAME = "DeliverOrder";

    private final String productName;
    private final double orderTotal;
    private final long customerId;

    public DeliverOrderCommand(final long customerId, final long messageId, final String productName, final double orderTotal) {
        super(messageId);
        this.customerId = customerId;
        this.productName = productName;
        this.orderTotal = orderTotal;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getProductName() {
        return productName;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliverOrderCommand that = (DeliverOrderCommand) o;
        return Double.compare(that.orderTotal, orderTotal) == 0 &&
                customerId == that.customerId &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, orderTotal, customerId);
    }
}
