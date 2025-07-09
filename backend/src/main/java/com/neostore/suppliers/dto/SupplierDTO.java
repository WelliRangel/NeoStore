package com.neostore.suppliers.dto;

import com.neostore.suppliers.validation.Cnpj;
import com.neostore.suppliers.validation.Email;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "id", "name", "email", "description", "cnpj" })
public record SupplierDTO(

        Long id,

        @NotBlank(message = "O nome não pode ser vazio")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @NotBlank(message = "O e-mail não pode ser vazio")
        @Email(message = "E-mail inválido")
        @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
        String email,

        @NotBlank(message = "A descrição não pode ser vazia")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String description,

        @NotBlank(message = "O CNPJ não pode ser vazio")
        @Cnpj(message = "CNPJ inválido")
        String cnpj
) {}