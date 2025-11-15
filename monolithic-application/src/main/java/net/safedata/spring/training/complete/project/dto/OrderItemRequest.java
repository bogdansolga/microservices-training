package net.safedata.spring.training.complete.project.dto;

public record OrderItemRequest(
    int menuItemId,
    int quantity
) {}
