import type { Supplier, ApiResponse, ImportResponse } from "@/types/supplier"
import { API_CONFIG } from "@/utils/constants"

class SupplierService {
  private async handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
      let errorMessage = `HTTP error! status: ${response.status}`

      try {
        const errorData = await response.json()
        errorMessage = errorData.message || errorMessage
      } catch {
        // If JSON parsing fails, use the default error message
      }

      throw new Error(errorMessage)
    }

    return response.json() as Promise<T>
  }

  private async fetchWithTimeout(url: string, options: RequestInit = {}): Promise<Response> {
    const controller = new AbortController()
    const timeoutId = setTimeout(() => controller.abort(), API_CONFIG.TIMEOUT)

    try {
      const response = await fetch(url, {
        ...options,
        signal: controller.signal,
        headers: {
          "Content-Type": "application/json",
          ...options.headers,
        },
      })
      clearTimeout(timeoutId)
      return response
    } catch (error) {
      clearTimeout(timeoutId)
      if (error instanceof Error && error.name === "AbortError") {
        throw new Error("Request timeout")
      }
      throw error
    }
  }

  async getSuppliers(page = 1, pageSize = 5): Promise<ApiResponse<Supplier[]>> {
    const url = new URL(`${API_CONFIG.BASE_URL}/suppliers/`)
    url.searchParams.set("page", page.toString())
    url.searchParams.set("pageSize", pageSize.toString())

    const response = await this.fetchWithTimeout(url.toString())
    return this.handleResponse<ApiResponse<Supplier[]>>(response)
  }

  async createSupplier(supplier: Omit<Supplier, "id">): Promise<Supplier> {
    const response = await this.fetchWithTimeout(`${API_CONFIG.BASE_URL}/suppliers/`, {
      method: "POST",
      body: JSON.stringify(supplier),
    })
    return this.handleResponse<Supplier>(response)
  }

  async updateSupplier(id: number, supplier: Omit<Supplier, "id">): Promise<Supplier> {
    const response = await this.fetchWithTimeout(`${API_CONFIG.BASE_URL}/suppliers/${id}`, {
      method: "PUT",
      body: JSON.stringify(supplier),
    })
    return this.handleResponse<Supplier>(response)
  }

  async deleteSupplier(id: number): Promise<void> {
    const response = await this.fetchWithTimeout(`${API_CONFIG.BASE_URL}/suppliers/${id}`, {
      method: "DELETE",
    })

    if (!response.ok) {
      let errorMessage = `HTTP error! status: ${response.status}`
      try {
        const errorData = await response.json()
        errorMessage = errorData.message || errorMessage
      } catch {
        // If JSON parsing fails, use the default error message
      }
      throw new Error(errorMessage)
    }
  }

  async importSuppliers(suppliers: Omit<Supplier, "id">[]): Promise<ImportResponse> {
    const response = await this.fetchWithTimeout(`${API_CONFIG.BASE_URL}/suppliers/import`, {
      method: "POST",
      body: JSON.stringify(suppliers),
    })
    return this.handleResponse<ImportResponse>(response)
  }
}

export const supplierService = new SupplierService()
