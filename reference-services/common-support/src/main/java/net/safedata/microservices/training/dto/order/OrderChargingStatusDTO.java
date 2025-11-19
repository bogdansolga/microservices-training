package net.safedata.microservices.training.dto.order;

import java.io.Serializable;
import java.util.Optional;

public record OrderChargingStatusDTO(boolean successful, String failureReason) implements Serializable {

    public static OrderChargingStatusDTO success() {
        return new OrderChargingStatusDTO(true, null);
    }

    public Optional<String> getFailureReason() {
        return Optional.ofNullable(failureReason);
    }
}
