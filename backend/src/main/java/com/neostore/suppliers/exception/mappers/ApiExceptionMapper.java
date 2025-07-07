package com.neostore.suppliers.exception.mappers;

import com.neostore.suppliers.api.payload.ApiErrorResponse;
import com.neostore.suppliers.api.payload.FieldError;
import com.neostore.suppliers.exception.ApiException;
import com.neostore.suppliers.exception.BusinessRuleException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    private static final Logger LOG = Logger.getLogger(ApiExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ApiException exception) {
        LOG.error("API Exception: ", exception);

        Response.Status status = exception.getStatus();
        int statusCode = status.getStatusCode();
        String errorType = status.getReasonPhrase();

        List<FieldError> fieldErrors = Collections.emptyList();

        // Se for BusinessRuleException, recupera os fieldErrors
        if (exception instanceof BusinessRuleException bre) {
            fieldErrors = bre.getFieldErrors();
        }

        ApiErrorResponse error = new ApiErrorResponse(
                statusCode,
                errorType,
                uriInfo.getPath(),
                Instant.now(),
                fieldErrors
        );

        return Response.status(status)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
