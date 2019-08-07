package net.safedata.microservices.training.dto.order;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class OrderDTO implements Serializable {

    private final long customerId;
    private final long orderId;
    private final List<OrderItemDTO> orderItems;

    public OrderDTO(long customerId, long orderId, List<OrderItemDTO> orderItems) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderItems = orderItems;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public double getOrderTotal() {
        return orderItems.stream()
                         .mapToDouble(OrderItemDTO::getProductPrice)
                         .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return customerId == orderDTO.customerId &&
                orderId == orderDTO.orderId &&
                Objects.equals(orderItems, orderDTO.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderId, orderItems);
    }
}
