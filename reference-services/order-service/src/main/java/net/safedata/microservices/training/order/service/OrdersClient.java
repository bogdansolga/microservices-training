package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.helper.MessageCreator;
import net.safedata.microservices.training.order.channel.InboundChannels;
import net.safedata.microservices.training.message.command.order.CreateOrderCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrdersClient {

    private static final AtomicLong CUSTOMER_ID_GENERATOR = new AtomicLong(243);

    private static final AtomicLong ORDER_AMOUNT_GENERATOR = new AtomicLong(200);

    private final InboundChannels inboundChannels;

    @Autowired
    public OrdersClient(final InboundChannels inboundChannels) {
        this.inboundChannels = inboundChannels;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishOrderCreationMessage() {
        final long nextCustomerId = CUSTOMER_ID_GENERATOR.addAndGet(5);
        final long nextOrderAmount = ORDER_AMOUNT_GENERATOR.addAndGet(125);

        inboundChannels.createOrder()
                       .send(MessageCreator.create(
           new CreateOrderCommand(nextCustomerId, 8234L, "An useful tablet", nextOrderAmount)));
    }
}
