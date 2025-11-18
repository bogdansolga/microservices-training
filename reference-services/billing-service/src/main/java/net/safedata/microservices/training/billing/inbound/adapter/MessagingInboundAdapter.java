package net.safedata.microservices.training.billing.inbound.adapter;

import net.safedata.microservices.training.billing.inbound.port.MessagingInboundPort;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
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
    public MessagingInboundAdapter(final MessagingInboundPort messagingInboundPort) {
        this.messagingInboundPort = messagingInboundPort;
    }

    @Bean
    public Consumer<ChargeOrderCommand> chargeOrder() {
        return chargeOrderCommand -> {
            LOGGER.debug("Received a '{}' command for the order with the ID {} of the customer with the ID {}",
                    chargeOrderCommand.getName(), chargeOrderCommand.getOrderId(), chargeOrderCommand.getCustomerId());

            messagingInboundPort.chargeOrder(chargeOrderCommand);
        };
    }
}
