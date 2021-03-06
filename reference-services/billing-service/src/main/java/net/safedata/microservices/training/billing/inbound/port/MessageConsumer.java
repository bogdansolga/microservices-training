package net.safedata.microservices.training.billing.inbound.port;

import net.safedata.microservices.training.billing.channel.InboundChannels;
import net.safedata.microservices.training.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.marker.message.Channels;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(InboundChannels.class)
public class MessageConsumer implements InboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    private final BillingService billingService;

    @Autowired
    public MessageConsumer(final BillingService billingService) {
        this.billingService = billingService;
    }

    @StreamListener(Channels.Commands.CHARGE_ORDER)
    public void chargeOrder(final ChargeOrderCommand chargeOrderCommand) {
        LOGGER.debug("Received a '{}' command for the order with the ID {} of the customer with the ID {}",
                chargeOrderCommand.getName(), chargeOrderCommand.getOrderId(), chargeOrderCommand.getCustomerId());

        billingService.chargeOrder(chargeOrderCommand);
    }
}
