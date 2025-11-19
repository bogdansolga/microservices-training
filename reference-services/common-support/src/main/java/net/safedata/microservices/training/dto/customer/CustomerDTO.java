package net.safedata.microservices.training.dto.customer;

import java.io.Serializable;

public record CustomerDTO(String name, String email) implements Serializable {}
