package net.safedata.microservices.training.order.inbound.port;

import net.safedata.microservices.training.dto.order.OrderDTO;
import net.safedata.microservices.training.marker.port.InboundPort;
import net.safedata.microservices.training.message.command.order.CreateOrderCommand;
import net.safedata.microservices.training.message.event.customer.CustomerUpdatedEvent;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderShippedEvent;

public interface MessagingInboundPort extends InboundPort {

    void createOrder(final CreateOrderCommand createOrderCommand);

    void handleCustomerUpdated(CustomerUpdatedEvent customerUpdatedEvent);

    void handleOrderCharged(OrderChargedEvent orderChargedEvent);

    void handleOrderNotCharged(OrderNotChargedEvent orderNotChargedEvent);

    void handleOrderShipped(OrderShippedEvent orderShippedEvent);
}
