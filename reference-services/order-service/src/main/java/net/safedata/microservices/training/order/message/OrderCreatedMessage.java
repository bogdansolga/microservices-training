package net.safedata.microservices.training.order.message;

public class OrderCreatedMessage extends AbstractMessage {

    private static final String NAME = "OrderCreated";

    private final long orderId;
    private final String productName;

    public OrderCreatedMessage(final long messageId, final long orderId, final String productName) {
        super(messageId);
        this.orderId = orderId;
        this.productName = productName;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
