package net.safedata.microservices.training.order.message;

import java.util.Objects;

public class CreateOrderMessage extends AbstractMessage {

    private static final String NAME = "CreateOrder";

    private final String productName;
    private final long productPrice;
    private final long customerId;

    public CreateOrderMessage(long messageId, String productName, long productPrice, long customerId) {
        super(messageId);
        this.productName = productName;
        this.productPrice = productPrice;
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrderMessage that = (CreateOrderMessage) o;
        return productPrice == that.productPrice &&
                customerId == that.customerId &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productPrice, customerId);
    }
}
