package com.neostore.suppliers.validation;

import com.neostore.suppliers.util.CnpjValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CnpjValidatorBean implements ConstraintValidator<Cnpj, String> {

    private String message;

    @Override
    public void initialize(Cnpj constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (CnpjValidator.isValid(value)) {
            return true;
        }
        // Customiza a mensagem incluindo o valor inválido
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("CNPJ inválido: " + value)
                .addConstraintViolation();
        return false;
    }
}