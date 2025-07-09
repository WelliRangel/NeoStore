package com.neostore.suppliers.api.payload;

import com.neostore.suppliers.dto.SupplierDTO;

public record ImportError(int index, SupplierDTO supplier, String error) {}