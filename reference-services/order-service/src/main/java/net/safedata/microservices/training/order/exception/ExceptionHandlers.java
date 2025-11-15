package net.safedata.microservices.training.order.exception;

import net.safedata.microservices.training.order.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The most common exception handlers
 *
 * @author bogdan.solga
 */
@ControllerAdvice
public class ExceptionHandlers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            RuntimeException.class,
            MessageHandlingException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO illegalArgumentException(final IllegalArgumentException e) {
        return new MessageDTO(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public MessageDTO internalServerError(final Exception e) {
        LOGGER.error(e.getMessage(), e);

        // can interact with an email sending / error reporting service

        return new MessageDTO("Internal server error");
    }
}
