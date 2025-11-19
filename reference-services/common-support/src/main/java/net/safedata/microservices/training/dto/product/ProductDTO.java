package net.safedata.microservices.training.dto.product;

import java.io.Serializable;

public record ProductDTO(long id, String name, double price) implements Serializable {}
