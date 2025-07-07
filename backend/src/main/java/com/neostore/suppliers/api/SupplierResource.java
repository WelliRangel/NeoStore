package com.neostore.suppliers.api;

import com.neostore.suppliers.dto.SupplierDTO;
import com.neostore.suppliers.service.SupplierService;
import com.neostore.suppliers.api.payload.PagedResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/api/v1/suppliers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SupplierResource {

    @Inject
    SupplierService service;

    @POST
    public Response create(@Valid SupplierDTO dto) {
        SupplierDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid SupplierDTO dto) {
        return Response.ok(service.update(id, dto)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response list(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("5") int pageSize
    ) {
        List<SupplierDTO> suppliers = service.findAll(page, pageSize);
        long total = service.count();
        return Response.ok(new PagedResponse<>(suppliers, total)).build();
    }

    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return Response.ok().build();
    }
}