package net.safedata.microservices.training.billing.outbound.adapter;

import net.safedata.microservices.training.billing.outbound.port.PaymentGatewayOutboundPort;
import net.safedata.microservices.training.dto.order.OrderChargingStatusDTO;
import net.safedata.microservices.training.marker.adapter.OutboundAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Outbound adapter for payment gateway operations.
 * Simulates integration with an external payment gateway (e.g., Braintree, Stripe).
 * In a real implementation, this would make HTTP calls to the payment gateway API.
 */
@Service
public class PaymentGatewayOutboundAdapter implements PaymentGatewayOutboundPort, OutboundAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentGatewayOutboundAdapter.class);

    /**
     * Simulates charging a payment method through an external payment gateway.
     * In production, this would integrate with services like Braintree, Stripe, etc.
     *
     * @param paymentMethodId the ID of the payment method to charge
     * @param amount the amount to charge
     * @return the result of the charging operation
     */
    @Override
    public synchronized OrderChargingStatusDTO charge(final int paymentMethodId, final double amount) {
        LOGGER.debug("Charging payment method {} for amount {}", paymentMethodId, amount);

        // Simulate payment gateway call - in production this would be an actual API call
        // Random success/failure for demonstration purposes
        boolean isSuccessful = System.currentTimeMillis() % 2 == 0;

        if (isSuccessful) {
            LOGGER.debug("Payment successful for method {} and amount {}", paymentMethodId, amount);
            return new OrderChargingStatusDTO();
        } else {
            LOGGER.debug("Payment failed for method {} and amount {}", paymentMethodId, amount);
            return new OrderChargingStatusDTO(false, "The card has expired");
        }
    }
}
