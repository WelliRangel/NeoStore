package com.neostore.suppliers.api.payload;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        String error,
        String message,
        int status,
        Instant timestamp,
        List<FieldError> fieldErrors
) {
    public ApiErrorResponse(String error, String message, int status, List<FieldError> fieldErrors) {
        this(error, message, status, Instant.now(), fieldErrors);
    }
}