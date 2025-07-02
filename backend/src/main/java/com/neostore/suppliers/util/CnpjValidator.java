package com.neostore.suppliers.util;


public class CnpjValidator {
    public static boolean isValid(String cnpj) {
        if (cnpj == null) return false;
        cnpj = cnpj.trim();
        if (cnpj.isEmpty()) return false;
        // Não permite nenhum whitespace interno
        for (int i = 0; i < cnpj.length(); i++) {
            char c = cnpj.charAt(i);
            if (Character.isWhitespace(c)) {
                // Se não está nas extremidades, é inválido
                return false;
            }
        }
        // Remove máscara
        cnpj = cnpj.replaceAll("\\D", "");
        if (cnpj.length() != 14) return false;
        // Checa se todos os dígitos são iguais
        char first = cnpj.charAt(0);
        boolean allEqual = true;
        for (int i = 1; i < 14; i++) {
            if (cnpj.charAt(i) != first) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) return false;
        // Validação dos dígitos verificadores
        int[] weight = {6,5,4,3,2,9,8,7,6,5,4,3,2};
        int sum = 0, digit1, digit2;
        for (int i = 0; i < 12; i++)
            sum += (cnpj.charAt(i) - '0') * weight[i+1];
        digit1 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        sum = 0;
        for (int i = 0; i < 13; i++)
            sum += (cnpj.charAt(i) - '0') * weight[i];
        digit2 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        return digit1 == (cnpj.charAt(12) - '0') && digit2 == (cnpj.charAt(13) - '0');
    }
}