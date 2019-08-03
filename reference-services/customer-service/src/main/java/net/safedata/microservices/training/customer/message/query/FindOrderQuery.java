package net.safedata.microservices.training.customer.message.query;

import net.safedata.microservices.training.customer.marker.message.Publisher;
import net.safedata.microservices.training.customer.marker.message.Service;
import net.safedata.microservices.training.customer.marker.message.Subscriber;

@Publisher(Service.ORDER_SERVICE)
@Subscriber(Service.BILLING_SERVICE)
public class FindOrderQuery extends AbstractQuery {

    private static final String NAME = "FindOrder";

    private final long customerId;
    private final long orderId;

    public FindOrderQuery(final long messageId, final long customerId, final long orderId) {
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
}
