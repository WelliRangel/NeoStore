package com.neostore.suppliers.exception;

import com.neostore.suppliers.api.payload.FieldError;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

/**
 * Exceção lançada quando uma regra de negócio é violada.
 */
public class BusinessRuleException extends ApiException {

  private final List<FieldError> fieldErrors;

  /**
   * Constrói uma nova BusinessRuleException com status HTTP 409 CONFLICT e sem erros de campo.
   *
   * @param message Mensagem detalhada da violação de regra de negócio.
   */
  public BusinessRuleException(String message) {
    this(message, Collections.emptyList());
  }

  /**
   * Constrói uma nova BusinessRuleException com status HTTP 409 CONFLICT e lista de erros de campo.
   *
   * @param message     Mensagem detalhada da violação de regra de negócio.
   * @param fieldErrors Lista de erros de campo (field, message)
   */
  public BusinessRuleException(String message, List<FieldError> fieldErrors) {
    super(Response.Status.CONFLICT, message, fieldErrors);
    this.fieldErrors = fieldErrors;
  }

  /**
   * Constrói uma nova BusinessRuleException com status HTTP 409 CONFLICT e causa.
   *
   * @param message Mensagem detalhada da violação de regra de negócio.
   * @param cause   Causa raiz da exceção.
   */
  public BusinessRuleException(String message, Throwable cause) {
    super(Response.Status.CONFLICT, message, Collections.emptyList(), cause);
    this.fieldErrors = Collections.emptyList();
  }

  /**
   * Retorna a lista de erros específicos de campos relacionados à exceção.
   *
   * @return Lista de FieldError, nunca nula.
   */
  @Override
  public List<FieldError> getFieldErrors() {
    return fieldErrors;
  }
}
