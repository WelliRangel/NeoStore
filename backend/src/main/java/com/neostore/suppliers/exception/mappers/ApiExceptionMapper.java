package com.neostore.suppliers.exception.mappers;

import com.neostore.suppliers.api.payload.ApiErrorResponse;
import com.neostore.suppliers.exception.ApiException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    private static final Logger LOG = Logger.getLogger(ApiExceptionMapper.class);

    @Override
    public Response toResponse(ApiException exception) {
        LOG.error("API Exception: ", exception);
        ApiErrorResponse error = new ApiErrorResponse(
                "Bad Request",
                exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode(),
                null
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}