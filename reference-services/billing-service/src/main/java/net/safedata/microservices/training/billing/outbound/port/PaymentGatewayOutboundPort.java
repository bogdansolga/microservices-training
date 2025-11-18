package net.safedata.microservices.training.billing.outbound.port;

import net.safedata.microservices.training.dto.order.OrderChargingStatusDTO;
import net.safedata.microservices.training.marker.port.OutboundPort;

/**
 * Outbound port for payment gateway operations.
 * Defines the contract for charging payment methods through external payment gateways.
 */
public interface PaymentGatewayOutboundPort extends OutboundPort {

    /**
     * Charges a payment method for the specified amount.
     *
     * @param paymentMethodId the ID of the payment method to charge
     * @param amount the amount to charge
     * @return the result of the charging operation
     */
    OrderChargingStatusDTO charge(int paymentMethodId, double amount);
}
