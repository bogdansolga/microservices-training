package net.safedata.microservices.training.dto;

import java.util.Objects;

public class OrderItemDTO extends AbstractDTO {

    private final long orderItemId;
    private final String productName;
    private final double productPrice;

    public OrderItemDTO(long orderItemId, String productName, double productPrice) {
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

    public double getProductPrice() {
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
