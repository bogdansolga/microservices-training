package net.safedata.microservices.training.shipping.inbound.port;

import net.safedata.microservices.training.dto.ShipmentDTO;
import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.message.command.order.ShipOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;
import net.safedata.microservices.training.shipping.outbound.port.MessagingOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.Set;

@Service
public class ShippingService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingService.class);

    private final MessagingOutboundPort messagingOutboundPort;

    @Autowired
    public ShippingService(final MessagingOutboundPort messagingOutboundPort) {
        this.messagingOutboundPort = messagingOutboundPort;
    }

    @Transactional
    public void shipOrder(final ShipOrderCommand shipOrderCommand) {
        final long customerId = shipOrderCommand.getCustomerId();
        final long orderId = shipOrderCommand.getOrderId();

        LOGGER.info("Shipping the order with the ID {} of the customer {}...", orderId, customerId);

        // retrieves the customer and the order, via async request response

        messagingOutboundPort.publishOrderShippedEvent(
                new OrderShippedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
    }

    public Set<ShipmentDTO> getShipmentsForCustomer(long customerId) {
        return null;
    }

    private long getNextMessageId() {
        return new Random(900000).nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return new Random(900000).nextLong();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
