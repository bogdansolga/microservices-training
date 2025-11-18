package net.safedata.microservices.training.delivery.service;

import net.safedata.microservices.training.delivery.domain.model.Delivery;
import net.safedata.microservices.training.delivery.inbound.port.MessagingInboundPort;
import net.safedata.microservices.training.delivery.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.delivery.outbound.port.PersistenceOutboundPort;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderDeliveredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService implements MessagingInboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryService.class);

    private final PersistenceOutboundPort persistencePort;
    private final MessagingOutboundPort messagingPort;

    @Autowired
    public DeliveryService(PersistenceOutboundPort persistencePort,
                          MessagingOutboundPort messagingPort) {
        this.persistencePort = persistencePort;
        this.messagingPort = messagingPort;
    }

    @Override
    public void deliverOrder(DeliverOrderCommand deliverOrderCommand) {
        LOGGER.info("Delivering an order for the customer with the ID {}...",
                deliverOrderCommand.getCustomerId());

        // Create and persist delivery
        Delivery delivery = new Delivery(
                deliverOrderCommand.getMessageId(),
                "Address for customer " + deliverOrderCommand.getCustomerId(),
                "DELIVERED"
        );
        persistencePort.save(delivery);

        OrderDeliveredEvent orderDeliveredEvent = createOrderDeliveredEvent(deliverOrderCommand);

        messagingPort.publishOrderDeliveredEvent(orderDeliveredEvent);
    }

    private OrderDeliveredEvent createOrderDeliveredEvent(DeliverOrderCommand deliverOrderCommand) {
        return new OrderDeliveredEvent(deliverOrderCommand.getMessageId(),
                120L, deliverOrderCommand.getCustomerId(), 250L);
    }
}
