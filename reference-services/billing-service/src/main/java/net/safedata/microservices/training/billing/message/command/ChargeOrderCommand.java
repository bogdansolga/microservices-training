package net.safedata.microservices.training.billing.message.command;

import net.safedata.microservices.training.billing.marker.message.Publisher;
import net.safedata.microservices.training.billing.marker.message.Service;
import net.safedata.microservices.training.billing.marker.message.Subscriber;

@Publisher(Service.ORDER_SERVICE)
@Subscriber(Service.BILLING_SERVICE)
public class ChargeOrderCommand extends AbstractCommand {

    private static final String NAME = "ChargeOrder";

    private final long customerId;
    private final long orderId;
    private final double orderTotal;
    private final Currency currency;

    public ChargeOrderCommand(final long messageId, final long customerId, final long orderId, final double orderTotal,
                              final Currency currency) {
        super(messageId);
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.currency = currency;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public enum Currency {
        EUR,
        USD
    }
}
