package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.order.dto.OrderDTO;
import net.safedata.microservices.training.order.events.OrderCreatedEvent;
import net.safedata.microservices.training.order.marker.InboundPort;
import net.safedata.microservices.training.order.ports.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public OrderService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Transactional
    public void createOrder(OrderDTO orderDTO) {
        LOGGER.info("Creating an order for the product {}...", orderDTO.getProductName());

        // TODO insert magic here

        messagingOutboundPort.publishEvent(new OrderCreatedEvent(230));
    }
}
