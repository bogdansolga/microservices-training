package net.safedata.microservices.training.order.message.command;

import net.safedata.microservices.training.order.marker.message.Publisher;
import net.safedata.microservices.training.order.marker.message.Service;
import net.safedata.microservices.training.order.marker.message.Subscriber;

import java.util.Objects;

@Publisher(Service.ORDER_SERVICE)
@Subscriber(Service.CUSTOMER_SERVICE)
public class CreateOrderCommand extends AbstractCommand {

    private static final String NAME = "CreateOrder";

    private final String productName;
    private final double orderTotal;
    private final long customerId;

    public CreateOrderCommand(final long customerId, final long messageId, final String productName, final double orderTotal) {
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
        CreateOrderCommand that = (CreateOrderCommand) o;
        return Double.compare(that.orderTotal, orderTotal) == 0 &&
                customerId == that.customerId &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, orderTotal, customerId);
    }
}
