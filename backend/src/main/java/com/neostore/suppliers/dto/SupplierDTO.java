package com.neostore.suppliers.dto;

import com.neostore.suppliers.validation.Cnpj;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "cnpj", "description", "email", "name" })
public record SupplierDTO(
        Long id,

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(max = 255)
        String description,

        @NotBlank
        @Cnpj
        String cnpj
) { }
