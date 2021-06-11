package com.microservices.workshop.shipping.mapper;

import com.microservices.workshop.shipping.domain.entity.Shipping;
import com.microservices.workshop.shipping.dto.ShippingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingMapper {

    Shipping asEntity(ShippingDTO chargeDTO);

    ShippingDTO asDTO(Shipping charge);
}
