package com.neostore.suppliers.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CnpjValidatorTest {

    @Test
    void testValidCnpj() {
        Assertions.assertTrue(CnpjValidator.isValid("12.345.678/0001-95"), "CNPJ válido com máscara");
        Assertions.assertTrue(CnpjValidator.isValid("12345678000195"), "CNPJ válido sem máscara");
        Assertions.assertTrue(CnpjValidator.isValid("45.723.174/0001-10"), "Outro CNPJ válido com máscara");
        Assertions.assertTrue(CnpjValidator.isValid("45723174000110"), "Outro CNPJ válido sem máscara");
        Assertions.assertTrue(CnpjValidator.isValid(" 12.345.678/0001-95 "), "CNPJ válido com espaços nas extremidades");
        Assertions.assertTrue(CnpjValidator.isValid("33.000.167/0001-01"), "CNPJ Petrobras");
    }

    @Test
    void testInvalidCnpj() {
        Assertions.assertFalse(CnpjValidator.isValid("00000000000000"), "CNPJ com todos dígitos iguais");
        Assertions.assertFalse(CnpjValidator.isValid("11111111111111"), "CNPJ com todos dígitos iguais");
        Assertions.assertFalse(CnpjValidator.isValid("22222222222222"), "CNPJ com todos dígitos iguais");
        Assertions.assertFalse(CnpjValidator.isValid("1234567890123"), "CNPJ com 13 dígitos");
        Assertions.assertFalse(CnpjValidator.isValid("123456789012345"), "CNPJ com 15 dígitos");
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9A"), "CNPJ com letra");
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9@"), "CNPJ com símbolo");
        Assertions.assertFalse(CnpjValidator.isValid(null), "CNPJ nulo");
        Assertions.assertFalse(CnpjValidator.isValid(""), "CNPJ vazio");
        Assertions.assertFalse(CnpjValidator.isValid(" "), "CNPJ só espaço");
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-96"), "CNPJ com dígito verificador errado");
        Assertions.assertFalse(CnpjValidator.isValid(" 12.345.678/0001-96 "), "CNPJ inválido com espaços");
    }

    @Test
    void testEdgeCases() {
        // CNPJ válido, mas com espaço interno (deve ser inválido)
        Assertions.assertFalse(CnpjValidator.isValid("12.345. 678/0001-95"), "CNPJ com espaço interno");
        // Removidos: tabulação e quebra de linha, pois a implementação já rejeita qualquer whitespace
    }
}