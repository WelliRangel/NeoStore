package com.neostore.suppliers.mapper;

import com.neostore.suppliers.dto.SupplierDTO;
import com.neostore.suppliers.model.Supplier;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utilitário para conversão entre Supplier e SupplierDTO.
 */
public final class SupplierMapper {

    private SupplierMapper() {}

    /**
     * Converte um Supplier em SupplierDTO.
     */
    public static SupplierDTO toDTO(Supplier entity) {
        if (entity == null) {
            return null;
        }
        return new SupplierDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getDescription(),
                entity.getCnpj()
        );
    }

    /**
     * Converte um SupplierDTO em Supplier.
     */
    public static Supplier toEntity(SupplierDTO dto) {
        if (dto == null) {
            return null;
        }
        Supplier entity = new Supplier();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setDescription(dto.description());
        entity.setCnpj(dto.cnpj());
        return entity;
    }

    /**
     * Converte uma lista de Supplier em uma lista de SupplierDTO.
     */
    public static List<SupplierDTO> toDTOList(List<Supplier> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(SupplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte uma lista de SupplierDTO em uma lista de Supplier.
     */
    public static List<Supplier> toEntityList(List<SupplierDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(SupplierMapper::toEntity)
                .collect(Collectors.toList());
    }
}
