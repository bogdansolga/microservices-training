package net.safedata.microservices.training.order.dto;

import java.io.Serializable;
import java.util.Objects;

public class OrderItemDTO implements Serializable {

    private final long orderItemId;
    private final String productName;
    private final long productPrice;

    public OrderItemDTO(long orderItemId, String productName, long productPrice) {
        this.orderItemId = orderItemId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public String getProductName() {
        return productName;
    }

    public long getProductPrice() {
        return productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDTO that = (OrderItemDTO) o;
        return orderItemId == that.orderItemId &&
                productPrice == that.productPrice &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, productName, productPrice);
    }
}
