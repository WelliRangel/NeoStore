package com.neostore.suppliers.api;

import com.neostore.suppliers.dto.SupplierDTO;
import com.neostore.suppliers.service.SupplierService;
import com.neostore.suppliers.api.payload.ImportResult;
import com.neostore.suppliers.api.payload.ImportError;
import com.neostore.suppliers.exception.ApiException;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/suppliers/import")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SupplierImportResource {

    @Inject
    private SupplierService service;

    @POST
    public Response importSuppliers(List<SupplierDTO> suppliers) {
        ImportResult result = new ImportResult();
        for (int i = 0; i < suppliers.size(); i++) {
            SupplierDTO dto = suppliers.get(i);
            try {
                service.create(dto);
                result.imported++;
            } catch (ApiException ex) {
                result.errors.add(new ImportError(i, dto, ex.getMessage()));
            } catch (ConstraintViolationException ex) {
                String msg = ex.getConstraintViolations().stream()
                        .map(v -> v.getMessage())
                        .collect(Collectors.joining("; "));
                result.errors.add(new ImportError(i, dto, msg));
            } catch (Exception ex) {
                result.errors.add(new ImportError(i, dto, "Unexpected error: " + ex.getMessage()));
            }
        }
        return Response.status(Response.Status.CREATED).entity(result).build();
    }
}