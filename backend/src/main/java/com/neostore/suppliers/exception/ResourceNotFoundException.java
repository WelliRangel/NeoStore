package com.neostore.suppliers.exception;

import jakarta.ws.rs.core.Response;

/**
 * Exceção lançada quando um recurso não é encontrado.
 */
public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String resource, Object id) {
        super(Response.Status.NOT_FOUND,
                String.format("%s não encontrado: %s", resource, id));
    }
}
