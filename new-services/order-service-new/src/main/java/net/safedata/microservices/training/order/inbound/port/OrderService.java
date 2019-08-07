package net.safedata.microservices.training.order.inbound.port;

import net.safedata.microservices.training.dto.order.OrderDTO;
import net.safedata.microservices.training.marker.port.InboundPort;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements InboundPort {

    public void createOrder(final OrderDTO orderDTO) {
        //TODO implement me
    }
}
