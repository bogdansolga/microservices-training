package net.safedata.spring.training.complete.project.dto;

import net.safedata.spring.training.complete.project.model.PaymentMethod;
import java.util.List;

public record CreateOrderRequest(
    int customerId,
    int restaurantId,
    List<OrderItemRequest> items,
    PaymentMethod paymentMethod
) {}
