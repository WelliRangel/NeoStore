package com.neostore.suppliers.validation;

import com.neostore.suppliers.util.CnpjValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CnpjValidatorBean implements ConstraintValidator<Cnpj, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CnpjValidator.isValid(value);
    }
}