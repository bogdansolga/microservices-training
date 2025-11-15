package com.epic.eats.dto;

import com.epic.eats.model.OrderStatus;
import com.epic.eats.model.PaymentMethod;
import com.epic.eats.model.PaymentStatus;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
    int orderId,
    int customerId,
    String customerName,
    int restaurantId,
    String restaurantName,
    LocalDateTime orderDate,
    OrderStatus status,
    double totalAmount,
    List<OrderItemDTO> items,
    PaymentMethod paymentMethod,
    PaymentStatus paymentStatus
) {
    public record OrderItemDTO(
        int menuItemId,
        String menuItemName,
        int quantity,
        double unitPrice,
        double subtotal
    ) {}
}
