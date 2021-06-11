package com.microservices.workshop.shipping.exception;

import com.microservices.workshop.billing.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // --> Spring centralized exception handling mechanism
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDTO badRequest(final IllegalArgumentException exception) {
        return new MessageDTO(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageDTO internalServerError(final Exception exception) {
        LOGGER.error(exception.getMessage(), exception);

        // optionally - sending the exception via email and/or to a telemetry service

        return new MessageDTO(exception.getMessage());
    }
}
