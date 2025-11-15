package com.epic.eats.dto;

public record OrderItemRequest(
    int menuItemId,
    int quantity
) {}
