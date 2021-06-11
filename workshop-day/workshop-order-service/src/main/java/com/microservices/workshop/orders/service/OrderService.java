package com.microservices.workshop.orders.service;

import com.microservices.workshop.orders.domain.entity.Order;
import com.microservices.workshop.orders.domain.repository.OrderRepository;
import com.microservices.workshop.orders.dto.MessageDTO;
import com.microservices.workshop.orders.dto.OrderDTO;
import com.microservices.workshop.orders.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false, // for write operations
            noRollbackFor = IllegalArgumentException.class // depending on the business context
    )
    public MessageDTO createOrder(OrderDTO orderDTO) {
        validate(orderDTO);

        final Order order = orderMapper.asEntity(orderDTO);

        orderRepository.save(order);
        LOGGER.info("The order with the ID {} was successfully saved", orderDTO.getCustomerId());

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
