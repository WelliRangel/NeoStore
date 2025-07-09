package com.neostore.suppliers.validation;

import com.neostore.suppliers.util.EmailValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidatorBean implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EmailValidator.isValid(value);
    }
}