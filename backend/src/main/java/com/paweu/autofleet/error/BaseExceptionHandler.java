package com.paweu.autofleet.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<ResponseApiError> buildResponse(ResponseApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
