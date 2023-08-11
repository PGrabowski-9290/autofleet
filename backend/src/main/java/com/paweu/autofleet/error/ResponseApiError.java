package com.paweu.autofleet.error;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseApiError(HttpStatusCode status, String message, List<ObjectError> allErrors) {
        this(HttpStatus.valueOf(status.value()),message);
        this.errors = allErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
