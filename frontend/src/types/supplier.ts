export interface Supplier {
  id: number
  name: string
  email: string
  description: string
  cnpj: string
}

export interface ApiResponse<T> {
  data: T
  total: number
}

export interface ApiError {
  error: string
  message: string
  status: number
  timestamp: string
}

export interface ImportError {
  index: number
  error: string
  supplier: Omit<Supplier, "id">
}

export interface ImportResponse {
  imported: number
  errors: ImportError[]
}
