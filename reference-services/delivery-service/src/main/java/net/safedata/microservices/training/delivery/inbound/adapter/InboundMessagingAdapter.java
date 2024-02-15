package net.safedata.microservices.training.delivery.inbound.adapter;

import net.safedata.microservices.training.delivery.inbound.port.InboundMessagingPort;
import net.safedata.microservices.training.message.command.order.DeliverOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class InboundMessagingAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(InboundMessagingAdapter.class);

    private final InboundMessagingPort inboundMessagingPort;

    @Autowired
    public InboundMessagingAdapter(InboundMessagingPort inboundMessagingPort) {
        this.inboundMessagingPort = inboundMessagingPort;
    }

    @Bean
    public Consumer<DeliverOrderCommand> deliverOrder() {
        return deliverOrderCommand -> {
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                    deliverOrderCommand.getName(), deliverOrderCommand.getProductName(), deliverOrderCommand.getCustomerId());

            inboundMessagingPort.deliverOrder(deliverOrderCommand);
        };
    }
}
