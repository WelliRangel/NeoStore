import type { Supplier, ApiResponse, ImportResponse } from "@/types/supplier"

const API_BASE_URL = "http://127.0.0.1:8080/neostore/api/v1"

class SupplierService {
  private async handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
    }
    return response.json()
  }

  async getSuppliers(page = 1, pageSize = 5): Promise<ApiResponse<Supplier[]>> {
    const response = await fetch(`${API_BASE_URL}/suppliers/?page=${page}&pageSize=${pageSize}`)
    return this.handleResponse<ApiResponse<Supplier[]>>(response)
  }

  async createSupplier(supplier: Omit<Supplier, "id">): Promise<Supplier> {
    const response = await fetch(`${API_BASE_URL}/suppliers/`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(supplier),
    })
    return this.handleResponse<Supplier>(response)
  }

  async updateSupplier(id: number, supplier: Omit<Supplier, "id">): Promise<Supplier> {
    const response = await fetch(`${API_BASE_URL}/suppliers/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(supplier),
    })
    return this.handleResponse<Supplier>(response)
  }

  async deleteSupplier(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/suppliers/${id}`, {
      method: "DELETE",
    })
    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
    }
  }

  async importSuppliers(suppliers: Omit<Supplier, "id">[]): Promise<ImportResponse> {
    const response = await fetch(`${API_BASE_URL}/suppliers/import`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(suppliers),
    })
    return this.handleResponse<ImportResponse>(response)
  }
}

export const supplierService = new SupplierService()
