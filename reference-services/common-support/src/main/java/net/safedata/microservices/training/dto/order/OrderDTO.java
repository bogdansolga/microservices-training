package net.safedata.microservices.training.dto.order;

import net.safedata.microservices.training.dto.product.OrderItemDTO;

import java.io.Serializable;
import java.util.List;

public record OrderDTO(long customerId, long orderId, List<OrderItemDTO> orderItems) implements Serializable {

    public double getOrderTotal() {
        return orderItems.stream()
                         .mapToDouble(OrderItemDTO::productPrice)
                         .sum();
    }
}
