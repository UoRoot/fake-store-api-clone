package com.fakestoreapi.clone.api.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fakestoreapi.clone.api.responses.ErrorMapResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMapResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ErrorMapResponse errorResponse = new ErrorMapResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                Map.of("", ""));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
