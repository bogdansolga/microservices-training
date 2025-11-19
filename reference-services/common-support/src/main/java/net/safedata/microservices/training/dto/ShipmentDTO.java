package net.safedata.microservices.training.dto;

import java.io.Serializable;

public record ShipmentDTO(String name, String email) implements Serializable {}
