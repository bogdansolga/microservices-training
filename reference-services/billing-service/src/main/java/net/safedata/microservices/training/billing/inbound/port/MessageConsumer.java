package net.safedata.microservices.training.billing.inbound.port;

import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MessageConsumer implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final BillingService billingService;

    @Autowired
    public MessageConsumer(final BillingService billingService) {
        this.billingService = billingService;
    }

    @Bean
    public Consumer<ChargeOrderCommand> chargeOrder() {
        return chargeOrderCommand -> {
            LOGGER.debug("Received a '{}' command for the order with the ID {} of the customer with the ID {}",
                    chargeOrderCommand.getName(), chargeOrderCommand.getOrderId(), chargeOrderCommand.getCustomerId());

            billingService.chargeOrder(chargeOrderCommand);
        };
    }
}
