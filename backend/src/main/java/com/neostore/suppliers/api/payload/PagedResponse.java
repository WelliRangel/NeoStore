package com.neostore.suppliers.api.payload;

import java.util.List;

/**
 * Represents a paginated API response.
 */
public record PagedResponse<T>(List<T> data, long total) {}