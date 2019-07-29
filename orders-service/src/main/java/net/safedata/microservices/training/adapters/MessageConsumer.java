package net.safedata.microservices.training.adapters;

import net.safedata.microservices.training.channels.InboundChannels;
import net.safedata.microservices.training.dto.OrderDTO;
import net.safedata.microservices.training.marker.InboundAdapter;
import net.safedata.microservices.training.service.OrderService;
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
