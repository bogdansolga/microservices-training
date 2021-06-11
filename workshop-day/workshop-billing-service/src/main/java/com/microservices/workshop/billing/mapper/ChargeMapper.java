package com.microservices.workshop.billing.mapper;

import com.microservices.workshop.billing.domain.entity.Charge;
import com.microservices.workshop.billing.dto.ChargeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChargeMapper {

    Charge asEntity(ChargeDTO chargeDTO);

    ChargeDTO asDTO(Charge charge);
}
