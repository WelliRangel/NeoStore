package com.neostore.suppliers.repository.impl;

import com.neostore.suppliers.model.Supplier;
import com.neostore.suppliers.repository.SupplierRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SupplierRepositoryImpl implements SupplierRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Supplier save(Supplier supplier) {
        if (supplier == null) throw new IllegalArgumentException("Supplier cannot be null");
        em.persist(supplier);
        return supplier;
    }

    @Override
    public Supplier update(Supplier supplier) {
        if (supplier == null) throw new IllegalArgumentException("Supplier cannot be null");
        return em.merge(supplier);
    }

    @Override
    public void delete(Long id) {
        if (id == null) return;
        Supplier supplier = em.find(Supplier.class, id);
        if (supplier != null) {
            em.remove(supplier);
        }
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(em.find(Supplier.class, id));
    }

    @Override
    public Optional<Supplier> findByCnpj(String cnpj) {
        if (cnpj == null) return Optional.empty();
        TypedQuery<Supplier> query = em.createQuery(
                "SELECT s FROM Supplier s WHERE s.cnpj = :cnpj", Supplier.class);
        query.setParameter("cnpj", cnpj);
        return query.getResultStream().findFirst();
    }

    @Override
    public Optional<Supplier> findByEmail(String email) {
        if (email == null) return Optional.empty();
        TypedQuery<Supplier> query = em.createQuery(
                "SELECT s FROM Supplier s WHERE s.email = :email", Supplier.class);
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