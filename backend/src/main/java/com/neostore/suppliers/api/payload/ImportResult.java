package com.neostore.suppliers.api.payload;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {
    public int imported;
    public List<ImportError> errors = new ArrayList<>();
}

