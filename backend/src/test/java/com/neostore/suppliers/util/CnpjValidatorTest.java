package com.neostore.suppliers.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CnpjValidatorTest {

    // CNPJs válidos (com e sem máscara)
    @Test
    void testValidCnpj() {
        Assertions.assertTrue(CnpjValidator.isValid("12.345.678/0001-95"));
        Assertions.assertTrue(CnpjValidator.isValid("12345678000195"));
        Assertions.assertTrue(CnpjValidator.isValid("45.723.174/0001-10"));
        Assertions.assertTrue(CnpjValidator.isValid("45723174000110"));
        Assertions.assertTrue(CnpjValidator.isValid("33.000.167/0001-01")); // Petrobras
        Assertions.assertTrue(CnpjValidator.isValid("00.623.904/0001-73")); // Nestlé
        Assertions.assertTrue(CnpjValidator.isValid("00623904000173"));     // Nestlé sem máscara
    }

    // CNPJs inválidos por formato, tamanho, caracteres, dígitos iguais, etc
    @Test
    void testInvalidCnpj() {
        // Todos dígitos iguais
        Assertions.assertFalse(CnpjValidator.isValid("00000000000000"));
        Assertions.assertFalse(CnpjValidator.isValid("11111111111111"));
        // Tamanho inválido
        Assertions.assertFalse(CnpjValidator.isValid("1234567890123")); // 13 dígitos
        Assertions.assertFalse(CnpjValidator.isValid("123456789012345")); // 15 dígitos
        // Caracteres inválidos
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9A"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9@"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9ç"));
        // Nulo, vazio
        Assertions.assertFalse(CnpjValidator.isValid(null));
        Assertions.assertFalse(CnpjValidator.isValid(""));
        // Dígito verificador errado
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-96"));
        // Máscara parcialmente correta
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001.95"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678-0001/95"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345678/0001-95"));
        // Letras
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/000A-95"));
        Assertions.assertFalse(CnpjValidator.isValid("ABCDEFGHIJKLMN"));
        // Unicode não invisível (fullwidth digits)
        Assertions.assertFalse(CnpjValidator.isValid("１２.３４５.６７８/０００１-９５"));
        // Só espaços
        Assertions.assertFalse(CnpjValidator.isValid("               "));
    }

    // Rejeita qualquer whitespace (ou quebra de linha/tab) em qualquer posição
    @Test
    void testRejectsAnyWhitespace() {
        Assertions.assertFalse(CnpjValidator.isValid(" 12.345.678/0001-95")); // espaço à esquerda
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-95 ")); // espaço à direita
        Assertions.assertFalse(CnpjValidator.isValid("12.345. 678/0001-95")); // espaço no meio
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/ 0001-95")); // espaço no meio
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001- 95")); // espaço no meio
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9 5")); // espaço no meio
        Assertions.assertFalse(CnpjValidator.isValid("12.345.\t678/0001-95")); // tab no meio
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-\n95")); // quebra de linha no meio
        Assertions.assertFalse(CnpjValidator.isValid("\t12.345.678/0001-95")); // tab à esquerda
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-95\n")); // quebra de linha à direita
    }

    // Rejeita caracteres invisíveis Unicode (ex: ZERO WIDTH SPACE, BOM, etc)
    @Test
    void testUnicodeInvisibleCharacters() {
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u200B-95")); // ZWSP
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u200E-95")); // LRM
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u200F-95")); // RLM
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u202A-95")); // LRE
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u202B-95")); // RLE
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u2060-95")); // WORD JOINER
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u202F-95")); // NARROW NO-BREAK SPACE
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001\u3000-95")); // IDEOGRAPHIC SPACE
    }

    // Não aceita caracteres especiais fora da máscara
    @Test
    void testSpecialCharacters() {
        Assertions.assertFalse(CnpjValidator.isValid("#12.345.678/0001-95#"));
        Assertions.assertFalse(CnpjValidator.isValid("12-345-678/0001-95")); // Máscara malformada
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9")); // Menos dígitos
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-95!"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-95$"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-95%"));
    }

    // Testa variantes de whitespace misturados
    @Test
    void testMultipleWhitespaceTypes() {
        Assertions.assertFalse(CnpjValidator.isValid("12.345.\t \n678/0001-95"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.\t\t\t678/0001-95"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.\n\n678/0001-95"));
        Assertions.assertFalse(CnpjValidator.isValid("12.345.\t \r\n678/0001-95"));
    }

    // Testa zeros à esquerda
    @Test
    void testLeadingZeros() {
        Assertions.assertTrue(CnpjValidator.isValid("00.623.904/0001-73")); // válido
        Assertions.assertTrue(CnpjValidator.isValid("00623904000173"));     // válido
        Assertions.assertFalse(CnpjValidator.isValid("00.000.000/0000-00")); // inválido (todos iguais)
    }

    // Testa entradas muito grandes (performance e robustez)
    @Test
    void testVeryLongInput() {
        StringBuilder sb = new StringBuilder("12.345.678/0001-95");
        for (int i = 0; i < 1000; i++) sb.append("0");
        Assertions.assertFalse(CnpjValidator.isValid(sb.toString()));
    }

    // Segurança: rejeita caracteres de controle, emojis, acentos, etc
    @Test
    void testSecurityNonAscii() {
        // Emojis
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-95😀"));
        // Caracteres de controle
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-\u0000\u0001\u0002-95"));
        // Acentos
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9é"));
        // Símbolos matemáticos
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9≤"));
        // Letras gregas
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9α"));
        // Caracteres árabes
        Assertions.assertFalse(CnpjValidator.isValid("١٢.٣٤٥.٦٧٨/٠٠٠١-٩٥"));
    }

    // Consistência: rejeita variantes visuais e homoglifos
    @Test
    void testHomoglyphsAndLookalikes() {
        // Dígitos cirílicos
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9\u0435"));
        // Dígitos gregos
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9\u03B5"));
        // Dígitos com diacríticos
        Assertions.assertFalse(CnpjValidator.isValid("12.345.678/0001-9\u0301"));
    }
}