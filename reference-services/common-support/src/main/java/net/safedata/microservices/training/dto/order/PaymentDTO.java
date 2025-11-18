package net.safedata.microservices.training.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDTO(
        Long id,
        Long customerId,
        Long orderId,
        BigDecimal amount,
        String status,
        LocalDateTime timestamp) {}
