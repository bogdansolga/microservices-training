package net.safedata.microservices.training.billing.service;

import net.safedata.microservices.training.billing.domain.model.Payment;
import net.safedata.microservices.training.billing.inbound.port.RestInboundPort;
import net.safedata.microservices.training.billing.inbound.port.MessagingInboundPort;
import net.safedata.microservices.training.billing.outbound.port.PersistenceOutboundPort;
import net.safedata.microservices.training.billing.outbound.port.MessagingOutboundPort;
import net.safedata.microservices.training.billing.outbound.port.PaymentGatewayOutboundPort;
import net.safedata.microservices.training.dto.order.OrderChargingStatusDTO;
import net.safedata.microservices.training.dto.order.PaymentDTO;
import net.safedata.microservices.training.message.command.order.ChargeOrderCommand;
import net.safedata.microservices.training.message.event.order.OrderChargedEvent;
import net.safedata.microservices.training.message.event.order.OrderNotChargedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class BillingService implements RestInboundPort, MessagingInboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingService.class);

    // Sequential ID generators for realistic ID generation
    private static final AtomicInteger PAYMENT_ID_COUNTER = new AtomicInteger(1);
    private static final AtomicLong MESSAGE_ID_COUNTER = new AtomicLong(300);
    private static final AtomicLong EVENT_ID_COUNTER = new AtomicLong(700);

    private final MessagingOutboundPort messagingOutboundPort;
    private final PersistenceOutboundPort persistenceOutboundPort;
    private final PaymentGatewayOutboundPort paymentGatewayPort;

    @Autowired
    public BillingService(final MessagingOutboundPort messagingOutboundPort,
                          final PersistenceOutboundPort persistenceOutboundPort,
                          final PaymentGatewayOutboundPort paymentGatewayPort) {
        this.messagingOutboundPort = messagingOutboundPort;
        this.persistenceOutboundPort = persistenceOutboundPort;
        this.paymentGatewayPort = paymentGatewayPort;
    }

    @Transactional
    public void chargeOrder(final ChargeOrderCommand chargeOrderCommand) {
        final long customerId = chargeOrderCommand.getCustomerId();
        final long orderId = chargeOrderCommand.getOrderId();
        final double orderTotal = chargeOrderCommand.getOrderTotal();

        LOGGER.info("Charging the customer with the ID {}, for the order with the ID {}, for {} {}...", customerId, orderId,
                orderTotal, chargeOrderCommand.getCurrency());

        final int usedPaymentMethod = getPaymentMethod();
        final OrderChargingStatusDTO orderChargingStatus = paymentGatewayPort.charge(usedPaymentMethod, orderTotal);

        sleepALittle();

        if (orderChargingStatus.successful()) {
            LOGGER.info("The customer {} was successfully charged for the order {}", customerId, orderId);

            // Persist the successful payment
            Payment payment = new Payment(customerId, orderId,
                    BigDecimal.valueOf(orderTotal), "CHARGED");
            persistenceOutboundPort.save(payment);

            messagingOutboundPort.publishOrderChargedEvent(
                    new OrderChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
        } else {
            final String failureReason = orderChargingStatus.getFailureReason()
                                                            .orElse("Cannot charge the card");
            LOGGER.warn("The customer {} could not be charged for the order {} - '{}'", customerId, orderId, failureReason);

            // Persist the failed payment
            Payment payment = new Payment(customerId, orderId,
                    BigDecimal.valueOf(orderTotal), "FAILED");
            persistenceOutboundPort.save(payment);

            messagingOutboundPort.publishOrderNotChargedEvent(
                    new OrderNotChargedEvent(getNextMessageId(), getNextEventId(), customerId, orderId,
                            failureReason)
            );
        }
    }

    @Transactional(readOnly = true)
    public List<PaymentDTO> getPaymentsForCustomer(long customerId) {
        LOGGER.info("Retrieving payments for customer {}", customerId);

        List<Payment> payments = persistenceOutboundPort.findByCustomerId(customerId);
        return payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getCustomerId(), payment.getOrderId(), payment.getAmount(),
                payment.getStatus(), payment.getTimestamp());
    }

    private int getPaymentMethod() {
        // return the user's payment methods
        return PAYMENT_ID_COUNTER.incrementAndGet();
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
            LOGGER.error(e.getMessage(), e);
        }
    }
}
