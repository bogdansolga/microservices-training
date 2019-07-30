package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.order.dto.OrderDTO;
import net.safedata.microservices.training.order.events.OrderCreatedEvent;
import net.safedata.microservices.training.order.marker.InboundPort;
import net.safedata.microservices.training.order.ports.MessagingOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService implements InboundPort {

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public OrderService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Transactional
    public void createOrder(OrderDTO orderDTO) {
        // insert magic here
        messagingOutboundPort.publishEvent(new OrderCreatedEvent(230));
    }
}
