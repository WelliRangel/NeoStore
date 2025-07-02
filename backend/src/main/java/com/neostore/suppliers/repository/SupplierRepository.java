// src/main/java/com/neostore/suppliers/repository/SupplierRepository.java
package com.neostore.suppliers.repository;

import com.neostore.suppliers.model.Supplier;
import java.util.List;
import java.util.Optional;

/**
 * Repositório de acesso a dados para a entidade Supplier.
 */
public interface SupplierRepository {

    /**
     * Persiste um novo fornecedor.
     * @param supplier entidade a ser salva (não pode ser null)
     * @return entidade persistida (com ID preenchido)
     * @throws IllegalArgumentException se supplier for null
     */
    Supplier save(Supplier supplier);

    /**
     * Atualiza um fornecedor existente.
     * @param supplier entidade a ser atualizada (não pode ser null)
     * @return entidade atualizada
     * @throws IllegalArgumentException se supplier for null
     */
    Supplier update(Supplier supplier);

    /**
     * Remove um fornecedor pelo ID.
     * @param id identificador do fornecedor (pode ser null, nesse caso não faz nada)
     */
    void delete(Long id);

    /**
     * Busca um fornecedor pelo ID.
     * @param id identificador do fornecedor
     * @return Optional com o fornecedor, se encontrado
     */
    Optional<Supplier> findById(Long id);

    /**
     * Busca um fornecedor pelo CNPJ.
     * @param cnpj CNPJ do fornecedor
     * @return Optional com o fornecedor, se encontrado
     */
    Optional<Supplier> findByCnpj(String cnpj);

    /**
     * Busca um fornecedor pelo e-mail.
     * @param email e-mail do fornecedor
     * @return Optional com o fornecedor, se encontrado
     */
    Optional<Supplier> findByEmail(String email);

    /**
     * Lista fornecedores com paginação.
     * @param page número da página (1-based)
     * @param pageSize tamanho da página; se <= 0, retorna todos
     * @return lista de fornecedores
     */
    List<Supplier> findAll(int page, int pageSize);

    /**
     * Conta o total de fornecedores cadastrados.
     * @return total de registros
     */
    long count();
}