package com.neostore.suppliers.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString("Origin");
        if ("http://localhost:3000".equals(origin) || "http://frontend:3000".equals(origin)) {
            responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", origin);
        }
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().putSingle("Access-Control-Max-Age", "3600");
    }
}