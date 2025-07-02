package com.neostore.suppliers.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailValidatorTest {

    @Test
    void testValidEmail() {
        Assertions.assertTrue(EmailValidator.isValid("test@email.com"));
        Assertions.assertTrue(EmailValidator.isValid("user.name+test@domain.co"));
        Assertions.assertTrue(EmailValidator.isValid("user@sub.domain.com"));
        Assertions.assertTrue(EmailValidator.isValid("user_name@domain.com"));
        Assertions.assertTrue(EmailValidator.isValid("user@domain.technology"));
    }

    @Test
    void testInvalidEmail() {
        Assertions.assertFalse(EmailValidator.isValid("invalid-email")); // Sem arroba
        Assertions.assertFalse(EmailValidator.isValid("user@"));         // Sem domínio
        Assertions.assertFalse(EmailValidator.isValid("@domain.com"));   // Sem local-part
        Assertions.assertFalse(EmailValidator.isValid(""));              // Vazio
        Assertions.assertFalse(EmailValidator.isValid(null));            // Nulo
        Assertions.assertFalse(EmailValidator.isValid(" user@domain.com")); // Espaço à esquerda
        Assertions.assertFalse(EmailValidator.isValid("user@domain.com ")); // Espaço à direita
        Assertions.assertFalse(EmailValidator.isValid("user@domain..com")); // Domínio com ..
        Assertions.assertFalse(EmailValidator.isValid("user@.com"));        // Domínio sem nome
        Assertions.assertFalse(EmailValidator.isValid("user@domain.c"));    // TLD com 1 caractere
        Assertions.assertFalse(EmailValidator.isValid("user@@domain.com")); // Duas arrobas
    }

    @Test
    void testEdgeCases() {
        Assertions.assertFalse(EmailValidator.isValid("user@domain.com\n")); // Quebra de linha
        Assertions.assertFalse(EmailValidator.isValid("user name@domain.com")); // Espaço no local-part
        Assertions.assertFalse(EmailValidator.isValid(".user@domain.com")); // Ponto no início do local-part
        Assertions.assertFalse(EmailValidator.isValid("user..name@domain.com")); // Ponto duplo no local-part
    }
}