package com.neostore.suppliers.api;

import com.neostore.suppliers.dto.SupplierDTO;
import com.neostore.suppliers.exception.ApiException;
import com.neostore.suppliers.service.SupplierService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;

@Path("/api/v1/suppliers/import")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SupplierImportResource {

    @Inject
    private SupplierService service;

    public static class ImportResult {
        public int imported;
        public List<ImportError> errors = new ArrayList<>();
    }

    public static class ImportError {
        public int index;
        public String error;
        public SupplierDTO supplier;

        public ImportError(int index, SupplierDTO supplier, String error) {
            this.index = index;
            this.supplier = supplier;
            this.error = error;
        }
    }

    @POST
    public Response importSuppliers(@Valid List<SupplierDTO> suppliers) {
        ImportResult result = new ImportResult();

        for (int i = 0; i < suppliers.size(); i++) {
            SupplierDTO dto = suppliers.get(i);
            try {
                service.create(dto);
                result.imported++;
            } catch (ApiException ex) {
                result.errors.add(new ImportError(i, dto, ex.getMessage()));
            } catch (Exception ex) {
                result.errors.add(new ImportError(i, dto, "Unexpected error: " + ex.getMessage()));
            }
        }
        return Response.status(Response.Status.CREATED).entity(result).build();
    }
}