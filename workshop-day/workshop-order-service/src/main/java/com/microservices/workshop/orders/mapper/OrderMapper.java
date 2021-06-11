package com.microservices.workshop.orders.mapper;

import com.microservices.workshop.orders.domain.entity.Order;
import com.microservices.workshop.orders.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order asEntity(OrderDTO orderDTO);

    OrderDTO asDTO(Order order);
}
