package com.neostore.suppliers.repository.impl;

import com.neostore.suppliers.model.Supplier;
import com.neostore.suppliers.repository.SupplierRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Implementação JPA do repositório de fornecedores.
 */
@ApplicationScoped
public class SupplierRepositoryImpl implements SupplierRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Supplier save(Supplier supplier) {
        em.persist(supplier);
        return supplier;
    }

    @Override
    public Supplier update(Supplier supplier) {
        return em.merge(supplier);
    }

    @Override
    public void delete(Long id) {
        Supplier entity = em.find(Supplier.class, id);
        if (entity == null) {
            throw new EntityNotFoundException("Supplier não encontrado: " + id);
        }
        em.remove(entity);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return Optional.ofNullable(em.find(Supplier.class, id));
    }

    @Override
    public Optional<Supplier> findByCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            return Optional.empty();
        }
        TypedQuery<Supplier> query = em.createQuery(
                "SELECT s FROM Supplier s WHERE s.cnpj = :cnpj", Supplier.class
        );
        query.setParameter("cnpj", cnpj);
        return query.getResultStream().findFirst();
    }

    @Override
    public Optional<Supplier> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        TypedQuery<Supplier> query = em.createQuery(
                "SELECT s FROM Supplier s WHERE s.email = :email", Supplier.class
        );
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Supplier> findAll(int page, int pageSize) {
        int safePage = Math.max(1, page);
        int safePageSize = Math.max(1, pageSize);

        return em.createQuery("SELECT s FROM Supplier s ORDER BY s.id", Supplier.class)
                .setFirstResult((safePage - 1) * safePageSize)
                .setMaxResults(safePageSize)
                .getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(s) FROM Supplier s", Long.class)
                .getSingleResult();
    }
}
