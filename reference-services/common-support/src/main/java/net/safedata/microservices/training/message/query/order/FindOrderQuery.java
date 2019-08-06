package net.safedata.microservices.training.message.query.order;

import net.safedata.microservices.training.marker.message.Channel;
import net.safedata.microservices.training.marker.message.MessageDetails;
import net.safedata.microservices.training.marker.message.Service;
import net.safedata.microservices.training.message.AbstractQuery;

@MessageDetails(
        publisher = Service.ORDER_SERVICE,
        subscribers = Service.BILLING_SERVICE,
        channel = Channel.FIND_ORDER
)
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
