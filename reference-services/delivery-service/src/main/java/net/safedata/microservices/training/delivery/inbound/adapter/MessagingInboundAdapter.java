package net.safedata.microservices.training.delivery.inbound.adapter;

import net.safedata.microservices.training.delivery.inbound.port.MessagingInboundPort;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessagingInboundAdapter implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingInboundAdapter.class);

    private final MessagingInboundPort messagingInboundPort;

    @Autowired
    public MessagingInboundAdapter(MessagingInboundPort messagingInboundPort) {
        this.messagingInboundPort = messagingInboundPort;
    }

    @Bean
    public Consumer<DeliverOrderCommand> deliverOrder() {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        return deliverOrderCommand -> {
            LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                    deliverOrderCommand.getName(), deliverOrderCommand.getProductName(), deliverOrderCommand.getCustomerId());

            messagingInboundPort.deliverOrder(deliverOrderCommand);
        };
    }
}
