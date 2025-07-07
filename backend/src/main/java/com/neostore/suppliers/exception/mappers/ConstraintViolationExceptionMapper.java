package com.neostore.suppliers.exception.mappers;

import com.neostore.suppliers.api.payload.ApiErrorResponse;
import com.neostore.suppliers.api.payload.FieldError;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapeia violações de validação de bean para respostas HTTP 400.
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException ex) {
        List<FieldError> fieldErrors = ex.getConstraintViolations().stream()
                .map(v -> new FieldError(
                        v.getPropertyPath().toString(),
                        v.getMessage()
                ))
                .collect(Collectors.toList());

        ApiErrorResponse error = new ApiErrorResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                Response.Status.BAD_REQUEST.getReasonPhrase(),
                uriInfo.getPath(),
                fieldErrors
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }
}
