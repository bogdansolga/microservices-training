package net.safedata.microservices.training.order.dto;

import java.io.Serializable;

public class OrderDTO implements Serializable {

    private final String productName;

    public OrderDTO(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
