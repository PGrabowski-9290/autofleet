package com.paweu.autofleet.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> buildResponse(ResponseApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    @NonNull
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(
            @NonNull WebExchangeBindException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status,
            @NonNull ServerWebExchange exchange) {
        final ResponseApiError apiError = new ResponseApiError(status, ex.getReason(), ex.getAllErrors());
        return Mono.just(buildResponse(apiError));
    }
}