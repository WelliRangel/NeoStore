package com.neostore.suppliers.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailValidatorTest {

    // --- E-mails válidos ---
    @Test
    void testValidEmails() {
        String[] valids = {
                "test@email.com",
                "user.name+test@domain.co",
                "user@sub.domain.com",
                "user_name@domain.com",
                "user@domain.technology",
                "a@b.cd", // menor válido
                "user@sub.sub2.sub3.domain.com",
                "user-name@domain-name.com",
                "user+tag@domain.com",
                "user123@domain123.com",
                "user@domain.verylongtldnamehere", // TLD 20 chars
                "user%name@domain.com" // ASCII permitido no local-part
        };
        for (String email : valids) {
            Assertions.assertTrue(EmailValidator.isValid(email), "Falhou para: " + email);
        }
    }

    // --- E-mails inválidos: formato, domínio, TLD, local-part, whitespace, unicode, tamanho ---
    @Test
    void testInvalidEmails() {
        String[] invalids = {
                // Formato básico
                "invalid-email", "user@", "@domain.com", "", null,
                // Espaços e whitespace
                " user@domain.com", "user@domain.com ", "user@do main.com", "user@domain.com\n", "user@domain.com\t", "user@domain.com\r",
                // Domínio e TLD
                "user@domain..com", "user@.com", "user@domain.c", "user@@domain.com", "user@domain.com.", "user@-domain.com", "user@domain-.com",
                "user@domain.c_m", "user@domain.123", "user@domain.c-m", "user@.domain.com", "user@domain.com-", "user@domain.abcdefghijklmnopqrstu", // TLD 21 chars
                // Local-part
                "user!@domain.com", "user#@domain.com", "user name@domain.com", ".user@domain.com", "user..name@domain.com", "user.@domain.com",
                // Unicode/acento
                "usér@domain.com", "user@domãin.com", "user@domain.cöm",
                // Unicode invisível
                "user@domain.com\u200B", "user@domain.com\uFEFF", "user@domain.com\u200E", "user@domain.com\u202A", "user@domain.com\u2060", "user@domain.com\u3000", "user@domain.com\u00A0",
                // Homoglyphs/fullwidth/emoji
                "а@domain.com", "ａ@domain.com", "user@domaіn.com", "user@domain.соm", "user@domain.com😀",
                // TLD > 20 chars
                "user@domain.technologytoolongforvalidation",
                // Tamanho
                "a".repeat(90) + "@domain.com", // 101 chars
                "a".repeat(90) + "@d.com",      // 101 chars
                "a".repeat(242) + "@d.com",     // 254 chars (RFC permite, mas regra local não)
                // Domínio label > 63 chars
                "user@" + "a".repeat(64) + ".com",
                // Segurança
                "<script>@domain.com", "user@domain.com;DROP TABLE users;"
        };
        for (String email : invalids) {
            Assertions.assertFalse(EmailValidator.isValid(email), "Aceitou: " + email);
        }
    }

    // --- Limites de tamanho válidos ---
    @Test
    void testValidLengthLimits() {
        String[] valids = {
                "a".repeat(64) + "@domain.com", // local-part 64, total 75
                "a".repeat(63) + "@domain.com", // local-part 63, total 74
                "a".repeat(64) + "@d.com"       // local-part 64, total 69
        };
        for (String email : valids) {
            Assertions.assertTrue(EmailValidator.isValid(email), "Falhou para: " + email);
        }
    }

    // --- ASCII printable no local-part (exceto proibidos) ---
    @Test
    void testAllAsciiPrintableInLocalPart() {
        for (char c = 33; c < 127; c++) {
            if ("!# @\"(),:;<>[]\\".indexOf(c) == -1) {
                String email = "user" + c + "name@domain.com";
                Assertions.assertTrue(EmailValidator.isValid(email), "Falhou para: " + email);
            }
        }
    }

    // --- ASCII control chars no local-part ---
    @Test
    void testAllAsciiControlChars() {
        for (char c = 0; c < 32; c++) {
            String email = "user" + c + "@domain.com";
            Assertions.assertFalse(EmailValidator.isValid(email), "Aceitou controle: " + (int) c);
        }
        String email = "user" + (char) 127 + "@domain.com";
        Assertions.assertFalse(EmailValidator.isValid(email), "Aceitou DEL");
    }

    // --- Limite de label do domínio ---
    @Test
    void testLabelLengthLimits() {
        String label63 = "a".repeat(63);
        String email = "user@" + label63 + ".com";
        Assertions.assertTrue(EmailValidator.isValid(email));
    }

    // --- Unicode normalização ---
    @Test
    void testUnicodeNormalization() {
        String nfc = "u\u00E9ser@domain.com"; // é
        String nfd = "u\u0065\u0301ser@domain.com"; // e + ́
        Assertions.assertFalse(EmailValidator.isValid(nfc));
        Assertions.assertFalse(EmailValidator.isValid(nfd));
    }
}