package com.fakestoreapi.clone.api.responses;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMapResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
}
