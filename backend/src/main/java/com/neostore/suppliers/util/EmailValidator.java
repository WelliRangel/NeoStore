package com.neostore.suppliers.util;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?![.])([a-zA-Z0-9_\\-+]+(?:\\.[a-zA-Z0-9_\\-+]+)*)@([a-zA-Z0-9\\-]+\\.)+([a-zA-Z]{2,20})$"
    );

    public static boolean isValid(String email) {
        if (email == null || email.isEmpty()) return false;
        // Não permite espaços, tabs ou quebras de linha nas extremidades
        if (!email.equals(email.trim())) return false;
        // Não permite nenhum whitespace em nenhuma posição
        if (email.matches(".*[\\s\\t\\n\\r].*")) return false;
        // Não permite mais de um '@'
        if (email.chars().filter(c -> c == '@').count() != 1) return false;
        int at = email.indexOf('@');
        if (at == -1) return false;
        String local = email.substring(0, at);
        if (local.startsWith(".") || local.endsWith(".")) return false;
        if (local.contains("..")) return false;
        // Não permite ! ou # no local-part
        if (local.contains("!") || local.contains("#")) return false;
        // Não permite espaço no local-part
        if (local.contains(" ")) return false;
        // Tamanho máximo
        if (email.length() > 100) return false;
        // Regex final
        if (!EMAIL_PATTERN.matcher(email).matches()) return false;
        // Checagem extra de TLD
        String domain = email.substring(at + 1);
        int lastDot = domain.lastIndexOf('.');
        if (lastDot == -1) return false;
        String tld = domain.substring(lastDot + 1);
        if (tld.length() < 2 || tld.length() > 20) return false;
        return true;
    }
}