package com.neostore.suppliers.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class CnpjValidator {

    // Apenas dígitos, ponto, barra e hífen, sem espaços em nenhuma posição
    private static final Pattern CNPJ_ALLOWED = Pattern.compile("^[0-9./-]+$");
    // Unicode invisível
    private static final Pattern INVISIBLE_UNICODE = Pattern.compile("[\\u200B-\\u200D\\uFEFF\\u200E\\u200F\\u202A-\\u202E\\u2060\\u202F\\u3000]");
    // Apenas dígitos
    private static final Pattern DIGITS_ONLY = Pattern.compile("\\d+");
    // Máscara padrão e puro
    private static final Pattern CNPJ_MASKED = Pattern.compile("^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$");
    private static final Pattern CNPJ_UNMASKED = Pattern.compile("^\\d{14}$");

    public static boolean isValid(String input) {
        if (input == null || input.isEmpty()) return false;

        // Rejeita qualquer caractere não-ASCII (inclusive fullwidth digits e homoglifos)
        if (!input.chars().allMatch(c -> c < 128)) return false;

        // Normaliza Unicode para evitar caracteres invisíveis disfarçados
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFKC);

        // Rejeita qualquer caractere invisível Unicode
        if (INVISIBLE_UNICODE.matcher(normalized).find()) return false;

        // Não permite nenhum whitespace em nenhuma posição
        if (normalized.matches(".*\\s.*")) return false;

        // Só permite dígitos e os caracteres . / -
        if (!CNPJ_ALLOWED.matcher(normalized).matches()) return false;

        // Só aceita formato puro ou máscara padrão
        if (!CNPJ_UNMASKED.matcher(normalized).matches() && !CNPJ_MASKED.matcher(normalized).matches()) return false;

        // Remove máscara (mantém apenas os números)
        String cnpj = normalized.replaceAll("\\D", "");
        if (!DIGITS_ONLY.matcher(cnpj).matches()) return false;
        if (cnpj.length() != 14) return false;

        // Rejeita CNPJs com todos os dígitos iguais
        if (cnpj.chars().distinct().count() == 1) return false;

        // Validação dos dígitos verificadores
        return hasValidCheckDigits(cnpj);
    }

    private static boolean hasValidCheckDigits(String cnpj) {
        int[] weight = {6,5,4,3,2,9,8,7,6,5,4,3,2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weight[i + 1];
        }
        int digit1 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weight[i];
        }
        int digit2 = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        return digit1 == Character.getNumericValue(cnpj.charAt(12)) &&
                digit2 == Character.getNumericValue(cnpj.charAt(13));
    }
}