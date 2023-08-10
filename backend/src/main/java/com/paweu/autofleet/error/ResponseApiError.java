package com.paweu.autofleet.error;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Data
public class ResponseApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ResponseApiError(HttpStatus status){
        this.status = status;
    }
    public ResponseApiError(HttpStatus status, String message) {
        this(status);
        this.message = message;
    }

    public ResponseApiError(HttpStatus status, String message, List<String> errors){
        this(status, message);
        this.errors = errors;
    }

    public ResponseApiError(HttpStatus status, String message, String error){
        this(status, message);
        this.errors = Arrays.asList(error);
    }
}
