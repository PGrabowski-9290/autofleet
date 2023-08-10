package com.paweu.autofleet.error;

import com.paweu.autofleet.error.exceptions.ResourceAlreadyExistException;
import com.paweu.autofleet.error.exceptions.ResourceNotFoundException;
import com.paweu.autofleet.error.exceptions.WrongCredentialsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResponseApiError> handleResourceNotFount(ResourceNotFoundException ex) {
        logger.warn(ex.getMessage());
        ResponseApiError apiError = new ResponseApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return buildResponse(apiError);
    }

    @ExceptionHandler({WrongCredentialsException.class})
    public ResponseEntity<ResponseApiError> handleWrongCredentials(WrongCredentialsException ex) {
        ResponseApiError apiError = new ResponseApiError(HttpStatus.UNAUTHORIZED, "error.global.wrong.credentials");
        return buildResponse(apiError);
    }

    @ExceptionHandler({ResourceAlreadyExistException.class})
    public ResponseEntity<ResponseApiError> handleResourceAlreadyExist(ResourceAlreadyExistException ex) {
        ResponseApiError apiError = new ResponseApiError(HttpStatus.CONFLICT, ex.getMessage());
        return buildResponse(apiError);
    }


}