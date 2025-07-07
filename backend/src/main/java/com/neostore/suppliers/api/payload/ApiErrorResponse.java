package com.neostore.suppliers.api.payload;

import java.time.Instant;
import java.util.List;

/**
 * Representa uma resposta padronizada de erro para a API.
 *
 * @param status       Código HTTP de resposta
 * @param error        Tipo do erro (ex: "Bad Request", "Not Found").
 * @param path         Caminho da requisição que gerou o erro
 * @param timestamp    Momento em que o erro ocorreu
 * @param fieldErrors  Lista de erros de campo (para validação)
 */
public record ApiErrorResponse(
        int status,
        String error,
        String path,
        Instant timestamp,
        List<FieldError> fieldErrors
) {
    /**
     * Construtor auxiliar que define timestamp automaticamente como Instant.now().
     */
    public ApiErrorResponse(int status, String error, String path, List<FieldError> fieldErrors) {
        this(status, error, path, Instant.now(), fieldErrors);
    }
}
