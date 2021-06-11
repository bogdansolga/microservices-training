package com.microservices.workshop.billing.service;

import com.microservices.workshop.billing.domain.entity.Charge;
import com.microservices.workshop.billing.domain.repository.ChargeRepository;
import com.microservices.workshop.billing.dto.ChargeDTO;
import com.microservices.workshop.billing.dto.MessageDTO;
import com.microservices.workshop.billing.mapper.ChargeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingService.class);

    private final ChargeMapper chargeMapper;
    private final ChargeRepository chargeRepository;

    @Autowired
    public BillingService(ChargeMapper chargeMapper, ChargeRepository chargeRepository) {
        this.chargeMapper = chargeMapper;
        this.chargeRepository = chargeRepository;
    }

    public MessageDTO chargeCustomer(ChargeDTO chargeDTO) {
        LOGGER.info("Saving a charge of {} for the customer with the ID {}...", chargeDTO.getAmount(),
                chargeDTO.getCustomerId());
        final Charge charge = chargeMapper.asEntity(chargeDTO);

        // performing validations / other business operations / payment gateway accesses / ...

        chargeRepository.save(charge);
        LOGGER.info("The customer with the ID {} was successfully charged", chargeDTO.getCustomerId());

        return new MessageDTO("The customer with the ID " + charge.getCustomerId() + " was charged");
    }
}
