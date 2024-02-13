package net.safedata.microservices.training.restaurant.inbound.adapter;

import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.restaurant.inbound.port.InboundMessagingPort;
import net.safedata.microservices.training.message.command.order.ProcessOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class InboundMessagingAdapter implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(InboundMessagingAdapter.class);

    private final InboundMessagingPort inboundMessagingPort;

    @Autowired
    public InboundMessagingAdapter(InboundMessagingPort inboundMessagingPort) {
        this.inboundMessagingPort = inboundMessagingPort;
    }

    @Bean
    public Consumer<ProcessOrderCommand> processOrder() {
        return processOrderCommand -> {
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            LOGGER.debug("Received a '{}' command, the ordered item is '{}', the customer ID is {}",
                    processOrderCommand.getName(), processOrderCommand.getProductName(), processOrderCommand.getCustomerId());

            inboundMessagingPort.processOrder(processOrderCommand);
        };
    }
}
