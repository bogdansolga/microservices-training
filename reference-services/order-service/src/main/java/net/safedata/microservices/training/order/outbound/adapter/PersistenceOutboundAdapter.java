package net.safedata.microservices.training.order.outbound.adapter;

import net.safedata.microservices.training.order.domain.model.Order;
import net.safedata.microservices.training.order.domain.repository.OrderRepository;
import net.safedata.microservices.training.order.outbound.port.PersistenceOutboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersistenceOutboundAdapter implements PersistenceOutboundPort {

    private final OrderRepository orderRepository;

    @Autowired
    public PersistenceOutboundAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED
    )
    public void save(Order order) {
        orderRepository.save(order);
    }
}
