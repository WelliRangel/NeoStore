package com.neostore.suppliers.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
