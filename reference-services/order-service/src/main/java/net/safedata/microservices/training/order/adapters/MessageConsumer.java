package net.safedata.microservices.training.order.adapters;

import net.safedata.microservices.training.order.channels.InboundChannels;
import net.safedata.microservices.training.order.dto.OrderDTO;
import net.safedata.microservices.training.order.marker.InboundAdapter;
import net.safedata.microservices.training.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(InboundChannels.class)
public class MessageConsumer implements InboundAdapter {

    private final OrderService orderService;

    @Autowired
    public MessageConsumer(final OrderService orderService) {
        this.orderService = orderService;
    }

    @StreamListener(InboundChannels.ORDERS)
    public void createOrder(final OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
    }
}
