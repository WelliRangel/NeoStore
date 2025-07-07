package com.neostore.suppliers.exception;

import com.neostore.suppliers.api.payload.FieldError;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

/**
 * Exceção base para erros da API, contendo status HTTP e erros de campo opcionais.
 */
public abstract class ApiException extends RuntimeException {

    private final Response.Status status;
    private final List<FieldError> fieldErrors;

    /**
     * Construtor com status e mensagem.
     */
    protected ApiException(Response.Status status, String message) {
        this(status, message, Collections.emptyList(), null);
    }

    /**
     * Construtor com status, mensagem e lista de erros de campo.
     */
    protected ApiException(Response.Status status, String message, List<FieldError> fieldErrors) {
        this(status, message, fieldErrors, null);
    }

    /**
     * Construtor completo com status, mensagem, erros de campo e causa.
     */
    protected ApiException(Response.Status status, String message, List<FieldError> fieldErrors, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.fieldErrors = fieldErrors != null ? fieldErrors : Collections.emptyList();
    }

    public Response.Status getStatus() {
        return status;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
