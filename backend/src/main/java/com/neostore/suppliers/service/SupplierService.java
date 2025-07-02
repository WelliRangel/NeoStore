package com.neostore.suppliers.service;

import com.neostore.suppliers.dto.SupplierDTO;
import jakarta.validation.Valid;
import java.util.List;

public interface SupplierService {
    SupplierDTO create(@Valid SupplierDTO dto);
    SupplierDTO update(Long id, @Valid SupplierDTO dto);
    void delete(Long id);
    SupplierDTO findById(Long id);
    List<SupplierDTO> findAll(int page, int pageSize);
    long count();
}
