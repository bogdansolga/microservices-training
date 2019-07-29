package net.safedata.microservices.training.adapters;

import net.safedata.microservices.training.ports.OrdersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceController {

    private final OrdersController ordersController;

    @Autowired
    public OrderServiceController(final OrdersController ordersController) {
        this.ordersController = ordersController;
    }
}
