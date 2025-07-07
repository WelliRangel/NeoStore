package com.neostore.suppliers.exception.mappers;

import com.neostore.suppliers.api.payload.ApiErrorResponse;
import com.neostore.suppliers.api.payload.FieldError;
import com.neostore.suppliers.exception.BusinessRuleException;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.Instant;
import java.util.Collections;

/**
 * Mapeia falhas de persistência para respostas HTTP.
 */
@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(PersistenceException ex) {
        // Verifica violação de constraint única
        if (ex.getCause() != null && ex.getCause().getMessage().contains("constraint")) {
            // Lança exceção de regra de negócio para ser tratada pelo ApiExceptionMapper
            throw new BusinessRuleException("Violação de restrição única no banco de dados");
        }

        ApiErrorResponse error = new ApiErrorResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                uriInfo.getPath(),
                Collections.emptyList()
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }
}