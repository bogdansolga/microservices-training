package com.microservices.workshop.orders.messaging;

import com.microservices.workshop.common.messaging.message.ChargeCustomer;
import com.microservices.workshop.orders.domain.entity.write.Order;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    public void publishChargeCustomerCommand(final Order order) {
        final ChargeCustomer chargeCustomer = new ChargeCustomer(order.getCustomerId(), order.getId(), order.getCorrelationId());
        System.out.println("Publishing the chargeCustomer command...");
    }
}
