package com.epic.eats.dto;

import com.epic.eats.model.PaymentMethod;
import java.util.List;

public record CreateOrderRequest(
    int customerId,
    int restaurantId,
    List<OrderItemRequest> items,
    PaymentMethod paymentMethod
) {}
