package com.neostore.suppliers.service.impl;

import com.neostore.suppliers.dto.SupplierDTO;
import com.neostore.suppliers.exception.ApiException;
import com.neostore.suppliers.mapper.SupplierMapper;
import com.neostore.suppliers.model.Supplier;
import com.neostore.suppliers.repository.SupplierRepository;
import com.neostore.suppliers.service.SupplierService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SupplierServiceImpl implements SupplierService {

    @Inject
    SupplierRepository repository;

    @Transactional
    @Override
    public SupplierDTO create(@Valid SupplierDTO dto) {
        checkCnpjUnique(dto.cnpj(), null);
        checkEmailUnique(dto.email(), null);
        Supplier supplier = SupplierMapper.toEntity(dto);
        Supplier saved = repository.save(supplier);
        return SupplierMapper.toDTO(saved);
    }

    @Transactional
    @Override
    public SupplierDTO update(Long id, @Valid SupplierDTO dto) {
        Supplier existing = getSupplierOrThrow(id);
        checkCnpjUnique(dto.cnpj(), id);
        checkEmailUnique(dto.email(), id);
        existing.setName(dto.name());
        existing.setEmail(dto.email());
        existing.setDescription(dto.description());
        existing.setCnpj(dto.cnpj());
        Supplier updated = repository.update(existing);
        return SupplierMapper.toDTO(updated);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        getSupplierOrThrow(id);
        repository.delete(id);
    }

    @Override
    public SupplierDTO findById(Long id) {
        return repository.findById(id)
                .map(SupplierMapper::toDTO)
                .orElseThrow(() -> new ApiException("Supplier not found."));
    }

    @Override
    public List<SupplierDTO> findAll(int page, int pageSize) {
        return repository.findAll(page, pageSize)
                .stream()
                .map(SupplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return repository.count();
    }

    private Supplier getSupplierOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException("Supplier not found."));
    }

    private void checkCnpjUnique(String cnpj, Long ignoreId) {
        repository.findByCnpj(cnpj)
                .filter(s -> ignoreId == null || !s.getId().equals(ignoreId))
                .ifPresent(s -> {
                    throw new ApiException("CNPJ already registered.");
                });
    }

    private void checkEmailUnique(String email, Long ignoreId) {
        repository.findByEmail(email)
                .filter(s -> ignoreId == null || !s.getId().equals(ignoreId))
                .ifPresent(s -> {
                    throw new ApiException("Email already registered.");
                });
    }
}