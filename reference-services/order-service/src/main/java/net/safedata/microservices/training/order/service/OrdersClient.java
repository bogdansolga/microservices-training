package net.safedata.microservices.training.order.service;

import net.safedata.microservices.training.message.command.order.CreateOrderCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Service
public class OrdersClient {

    private static final AtomicLong CUSTOMER_ID_GENERATOR = new AtomicLong(243);
    private static final AtomicLong ORDER_AMOUNT_GENERATOR = new AtomicLong(200);
    private static final AtomicLong MESSAGE_ID_GENERATOR = new AtomicLong(100);

    private final StreamBridge streamBridge;

    @Autowired
    public OrdersClient(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public Supplier<CreateOrderCommand> orderProducer() {
        final long nextCustomerId = CUSTOMER_ID_GENERATOR.addAndGet(5);
        final long nextOrderAmount = ORDER_AMOUNT_GENERATOR.addAndGet(125);
        final long messageId = MESSAGE_ID_GENERATOR.incrementAndGet();

        return () -> new CreateOrderCommand(nextCustomerId, messageId, "Great Pizza #" + messageId, nextOrderAmount);
    }

    //@Scheduled(fixedDelay = 20000)
    public void publishOrderCreationMessage() {
        streamBridge.send("orderProducer-out-0", orderProducer().get());
    }
}
