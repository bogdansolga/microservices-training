package net.safedata.microservices.training.billing.inbound.port;

import net.safedata.microservices.training.billing.service.PaymentService;
import net.safedata.microservices.training.dto.order.OrderChargingStatusDTO;
import net.safedata.microservices.training.dto.order.PaymentDTO;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import net.safedata.microservices.training.billing.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.marker.port.InboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BillingService implements InboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingService.class);

    // Sequential ID generators for realistic ID generation
    private static final AtomicLong MESSAGE_ID_COUNTER = new AtomicLong(3000);
    private static final AtomicLong EVENT_ID_COUNTER = new AtomicLong(7000);

    private final MessagingOutboundPort messagingOutboundPort;
    private final PaymentService paymentService;

    @Autowired
    public BillingService(final MessagingOutboundPort messagingOutboundPort, final PaymentService paymentService) {
        this.messagingOutboundPort = messagingOutboundPort;
        this.paymentService = paymentService;
    }

    @Transactional
    public void chargeOrder(final ChargeOrderCommand chargeOrderCommand) {
        final long customerId = chargeOrderCommand.getCustomerId();
        final long orderId = chargeOrderCommand.getOrderId();
        final double orderTotal = chargeOrderCommand.getOrderTotal();

        LOGGER.info("Charging the customer with the ID {}, for the order with Id {}, for {} {}...", customerId, orderId,
                orderTotal, chargeOrderCommand.getCurrency());

        final int usedPaymentMethod = getPaymentMethod();
        final OrderChargingStatusDTO orderChargingStatus = paymentService.charge(usedPaymentMethod, orderTotal);

        // TODO insert magic here
        sleepALittle();

        if (orderChargingStatus.isSuccessful()) {
            LOGGER.info("The customer {} was successfully charged for the order {}", customerId, orderId);
            messagingOutboundPort.publishOrderChargedEvent(
                    new OrderChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
        } else {
            final String failureReason = orderChargingStatus.getFailureReason()
                                                            .orElse("Cannot charge the card");
            LOGGER.warn("The customer {} could not be charged for the order {} - '{}'", customerId, orderId, failureReason);
            messagingOutboundPort.publishOrderNotChargedEvent(
                    new OrderNotChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId,
                            failureReason)
            );
        }
    }

    @Transactional
    public List<PaymentDTO> getPaymentsForCustomer(long customerId) {
        // TODO insert magic here
        return new ArrayList<>();
    }

    private int getPaymentMethod() {
        // return the user's payment methods
        return 2;
    }

    private long getNextMessageId() {
        return MESSAGE_ID_COUNTER.incrementAndGet();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return EVENT_ID_COUNTER.incrementAndGet();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
