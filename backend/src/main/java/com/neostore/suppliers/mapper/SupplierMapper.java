package com.neostore.suppliers.mapper;

import com.neostore.suppliers.model.Supplier;
import com.neostore.suppliers.dto.SupplierDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class SupplierMapper {

    private SupplierMapper() {
    }

    public static SupplierDTO toDTO(Supplier entity) {
        if (entity == null) return null;
        return new SupplierDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getDescription(),
                entity.getCnpj()
        );
    }

    public static Supplier toEntity(SupplierDTO dto) {
        if (dto == null) return null;
        Supplier entity = new Supplier();
        entity.setId(dto.id());          
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setDescription(dto.description());
        entity.setCnpj(dto.cnpj());
        return entity;
    }

    // Métodos utilitários para listas
    public static List<SupplierDTO> toDTOList(List<Supplier> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(SupplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Supplier> toEntityList(List<SupplierDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(SupplierMapper::toEntity)
                .collect(Collectors.toList());
    }
}
