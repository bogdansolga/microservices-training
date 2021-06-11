package com.microservices.workshop.billing.service;

import com.microservices.workshop.billing.domain.entity.Charge;
import com.microservices.workshop.billing.domain.repository.ChargeRepository;
import com.microservices.workshop.billing.dto.ChargeDTO;
import com.microservices.workshop.billing.dto.MessageDTO;
import com.microservices.workshop.billing.mapper.ChargeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    // absolutely mandatory on write operations (Create, Update, Delete)
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false, // for write operations
            noRollbackFor = IllegalArgumentException.class // depending on the business context
    )
    public MessageDTO chargeCustomer(ChargeDTO chargeDTO) {
        // always perform the validations as the first step of the processing
        validate(chargeDTO);

        // Logging levels are ordered by severity
        // FATAL > ERROR > WARN > INFO > DEBUG > TRACE
        LOGGER.info("Saving a charge of {} for the customer with the ID {}...", chargeDTO.getAmount(),
                chargeDTO.getCustomerId());
        final Charge charge = chargeMapper.asEntity(chargeDTO);

        // performing validations / other business operations / payment gateway accesses / ...

        try {
            chargeMapper.asEntity(chargeDTO);
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage()); // for non disrupting warnings / exceptions   --> application / context dependant
            LOGGER.error(ex.getMessage(), ex); // for serious / stopping errors         --> application / context dependant
        }

        chargeRepository.save(charge);
        LOGGER.info("The customer with the ID {} was successfully charged", chargeDTO.getCustomerId());

        return new MessageDTO("The customer with the ID " + charge.getCustomerId() + " was charged");
    }

    private void validate(ChargeDTO chargeDTO) {
        // perform the validations
    }

    // maybe it's optional on read operations
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = true
    )
    public MessageDTO get(int id) {
        // use a fail-fast approach - perform the validations at the beginning of the processing, throw an exception if needed,
        // continue the processing afterwards

        throw new IllegalArgumentException("There is no charge with the ID " + id);
    }

    @Async
    public void performAsyncProcessing() {
        // perform magic
    }
}
