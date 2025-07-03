package com.neostore.suppliers.util;

import java.util.regex.Pattern;

public class EmailValidator {
    // Permite todos ASCII printable exceto ! # espaço @ " ( ) , : ; < > [ ] \ no local-part
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?![.])([a-zA-Z0-9$%&'*+/=?^_`{|}~.-]+)@([a-zA-Z0-9\\-]+\\.)+([a-zA-Z]{2,20})$"
    );
    // Unicode invisível (ZWSP, BOM, LRM, etc)
    private static final Pattern INVISIBLE_UNICODE = Pattern.compile(
            "[\\u200B-\\u200D\\uFEFF\\u200E\\u200F\\u202A-\\u202E\\u2060\\u202F\\u3000]"
    );
    // Proibidos no local-part por RFC e pelos testes
    private static final String LOCAL_PROHIBITED = "!# @\"(),:;<>[]\\";

    public static boolean isValid(String email) {
        if (email == null || email.isEmpty()) return false;
        if (!email.equals(email.trim())) return false;
        if (email.matches(".*[\\s\\t\\n\\r].*")) return false;
        if (INVISIBLE_UNICODE.matcher(email).find()) return false;
        if (email.chars().filter(c -> c == '@').count() != 1) return false;
        if (!email.chars().allMatch(c -> c < 128)) return false;
        if (email.length() > 100) return false;

        int at = email.indexOf('@');
        if (at == -1) return false;
        String local = email.substring(0, at);
        String domain = email.substring(at + 1);

        // Limite do local-part: 1 a 64 caracteres
        if (local.length() < 1 || local.length() > 64) return false;

        // Demais validações do local-part
        for (char c : local.toCharArray()) {
            if (c < 33 || c > 126) return false;
            if (LOCAL_PROHIBITED.indexOf(c) != -1) return false;
        }
        if (local.startsWith(".") || local.endsWith(".")) return false;
        if (local.contains("..")) return false;
        if (local.contains(" ")) return false;

        // Regex final (garante formato geral)
        if (!EMAIL_PATTERN.matcher(email).matches()) return false;

        // Checagem extra de TLD
        int lastDot = domain.lastIndexOf('.');
        if (lastDot == -1) return false;
        String tld = domain.substring(lastDot + 1);
        if (tld.length() < 2 || tld.length() > 20) return false;

        // Checagem de labels do domínio
        String[] labels = domain.split("\\.");
        for (String label : labels) {
            if (label.isEmpty()) return false;
            if (label.startsWith("-") || label.endsWith("-")) return false;
            if (label.length() > 63) return false;
        }
        return true;
    }
}