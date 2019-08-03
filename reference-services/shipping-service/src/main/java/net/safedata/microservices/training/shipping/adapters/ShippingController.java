package net.safedata.microservices.training.shipping.adapters;

import net.safedata.microservices.training.shipping.dto.ShipmentDTO;
import net.safedata.microservices.training.shipping.marker.adapter.InboundAdapter;
import net.safedata.microservices.training.shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice
@RequestMapping("/shipping")
public class ShippingController implements InboundAdapter {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(final ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/{customerId}")
    public Set<ShipmentDTO> getShipmentsForCustomer(@PathVariable final long customerId) {
        return shippingService.getShipmentsForCustomer(customerId);
    }
}
