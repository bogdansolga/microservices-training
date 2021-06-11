package com.microservices.workshop.billing.dto;

public class MessageDTO {

    private final String message;

    public MessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
