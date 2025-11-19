package net.safedata.microservices.training.dto.product;

import java.io.Serializable;

public record OrderItemDTO(long orderItemId, String productName, double productPrice) implements Serializable {}
