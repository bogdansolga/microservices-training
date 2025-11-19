package net.safedata.microservices.training.restaurant.inbound.adapter;

import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.restaurant.inbound.port.MessagingInboundPort;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
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
    public Consumer<ProcessOrderCommand> processOrder() {
        return processOrderCommand -> {
            LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                    processOrderCommand.getName(), processOrderCommand.getProductName(), processOrderCommand.getCustomerId());

            messagingInboundPort.processOrder(processOrderCommand);
        };
    }
}
