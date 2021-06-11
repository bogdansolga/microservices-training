package com.microservices.workshop.orders.service;

import com.microservices.workshop.orders.domain.entity.write.CreateOrderSaga;
import com.microservices.workshop.orders.domain.entity.write.CreateOrderSagaStep;
import com.microservices.workshop.orders.domain.entity.write.Order;
import com.microservices.workshop.orders.domain.entity.write.Status;
import com.microservices.workshop.orders.domain.repository.write.CreateOrderSagaRepository;
import com.microservices.workshop.orders.domain.repository.write.OrderRepository;
import com.microservices.workshop.orders.dto.MessageDTO;
import com.microservices.workshop.orders.dto.OrderDTO;
import com.microservices.workshop.orders.mapper.OrderMapper;
import com.microservices.workshop.orders.messaging.MessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final CreateOrderSagaRepository createOrderSagaRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final MessagePublisher messagePublisher;

    @Autowired
    public OrderService(CreateOrderSagaRepository createOrderSagaRepository,
                        OrderMapper orderMapper, OrderRepository orderRepository,
                        MessagePublisher messagePublisher) {
        this.createOrderSagaRepository = createOrderSagaRepository;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.messagePublisher = messagePublisher;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false, // for write operations
            noRollbackFor = IllegalArgumentException.class // depending on the business context
    )
    public MessageDTO createOrder(OrderDTO orderDTO) {
        validate(orderDTO);

        final Order order = orderMapper.asEntity(orderDTO);
        order.setStatus(Status.IN_PROCESSING);
        orderRepository.save(order);
        LOGGER.info("The order with the ID {} was successfully saved", orderDTO.getCustomerId());

        final CreateOrderSaga createOrderSaga = new CreateOrderSaga();
        final CreateOrderSagaStep createOrderSagaStep = new CreateOrderSagaStep();
        createOrderSagaStep.setStepName("orderInitiated");
        createOrderSaga.setSagaSteps(Set.of(createOrderSagaStep));
        createOrderSagaRepository.save(createOrderSaga);

        messagePublisher.publishChargeCustomerCommand(order);

        return new MessageDTO("The customer with the ID " + order.getCustomerId() + " was charged");
    }

    private void validate(OrderDTO orderDTO) {
        // perform the validations
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = true
    )
    public MessageDTO get(int id) {
        throw new IllegalArgumentException("There is no order with the ID " + id);
    }
}
