package com.microservices.workshop.billing.controller;

import com.microservices.workshop.billing.dto.ChargeDTO;
import com.microservices.workshop.billing.dto.MessageDTO;
import com.microservices.workshop.billing.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    @Autowired
    public BillingController(final BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public MessageDTO chargeCustomer(@RequestBody final ChargeDTO chargeDTO) {
        return billingService.chargeCustomer(chargeDTO);
    }
}
