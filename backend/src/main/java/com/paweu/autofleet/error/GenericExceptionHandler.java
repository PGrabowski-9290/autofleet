package com.paweu.autofleet.error;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(0)
public class GenericExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGenericException(final Exception ex) {
        logger.error("Uncaught error. Stack trace: " + ex + getFullStackTrace(ex));
        logger.error(ex);
        ResponseApiError responseApiError = new ResponseApiError(HttpStatus.INTERNAL_SERVER_ERROR, "error.generic.internal.server.error");
        return buildResponse(responseApiError);
    }

    private String getFullStackTrace(Exception ex) {
        return Arrays.stream(ex.getStackTrace())
                .map(Objects::toString)
                .collect(Collectors.joining("\n"));
    }
}
