import { useState, useEffect, useCallback, useRef } from "react"
import type { Supplier, ImportResponse } from "@/types/supplier"
import { supplierService } from "@/services/supplierService"

interface UseSuppliers {
  suppliers: Supplier[]
  total: number
  currentPage: number
  loading: boolean
  error: string | null
  fieldErrors: { field: string; message: string }[] | null
  loadSuppliers: (page?: number) => Promise<void>
  createSupplier: (supplier: Omit<Supplier, "id">) => Promise<void>
  updateSupplier: (id: number, supplier: Omit<Supplier, "id">) => Promise<void>
  deleteSupplier: (id: number) => Promise<void>
  importSuppliers: (file: File) => Promise<ImportResponse>
}

interface UseSupplierOptions {
  pageSize?: number
  onSuccess?: (message: string) => void
  onError?: (message: string) => void
}

export function useSuppliers({ pageSize = 5, onSuccess, onError }: UseSupplierOptions = {}): UseSuppliers {
  const [suppliers, setSuppliers] = useState<Supplier[]>([])
  const [total, setTotal] = useState(0)
  const [currentPage, setCurrentPage] = useState(1)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [fieldErrors, setFieldErrors] = useState<{ field: string; message: string }[] | null>(null)

  const onSuccessRef = useRef(onSuccess)
  const onErrorRef = useRef(onError)

  useEffect(() => { onSuccessRef.current = onSuccess }, [onSuccess])
  useEffect(() => { onErrorRef.current = onError }, [onError])

  const extractError = (err: any): { message: string, fieldErrors?: { field: string; message: string }[] } => {
    if (err?.fieldErrors && Array.isArray(err.fieldErrors) && err.fieldErrors.length > 0) {
      return {
        message: err.fieldErrors.map((e: any) => e.message).join("; "),
        fieldErrors: err.fieldErrors,
      }
    }
    return { message: err?.error || err?.message || "Erro desconhecido" }
  }

  const loadSuppliers = useCallback(
    async (page = 1) => {
      setLoading(true)
      setError(null)
      setFieldErrors(null)
      try {
        const response = await supplierService.getSuppliers(page, pageSize)
        setSuppliers(response.data)
        setTotal(response.total)
        setCurrentPage(page)
      } catch (err) {
        const { message, fieldErrors } = extractError(err)
        setError(message)
        setFieldErrors(fieldErrors ?? null)
        onErrorRef.current?.(message)
      } finally {
        setLoading(false)
      }
    },
    [pageSize],
  )

  const createSupplier = useCallback(
    async (supplierData: Omit<Supplier, "id">) => {
      setFieldErrors(null)
      try {
        await supplierService.createSupplier(supplierData)
        onSuccessRef.current?.("Fornecedor criado com sucesso!")
        await loadSuppliers(currentPage)
      } catch (err: any) {
        const { message, fieldErrors } = extractError(err)
        setFieldErrors(fieldErrors ?? null)
        onErrorRef.current?.(message)
        throw err
      }
    },
    [currentPage, loadSuppliers],
  )

  const updateSupplier = useCallback(
    async (id: number, supplierData: Omit<Supplier, "id">) => {
      setFieldErrors(null)
      try {
        await supplierService.updateSupplier(id, supplierData)
        onSuccessRef.current?.("Fornecedor atualizado com sucesso!")
        await loadSuppliers(currentPage)
      } catch (err: any) {
        const { message, fieldErrors } = extractError(err)
        setFieldErrors(fieldErrors ?? null)
        onErrorRef.current?.(message)
        throw err
      }
    },
    [currentPage, loadSuppliers],
  )

  const deleteSupplier = useCallback(
    async (id: number) => {
      if (!window.confirm("Tem certeza que deseja excluir este fornecedor?")) {
        return
      }
      setFieldErrors(null)
      try {
        await supplierService.deleteSupplier(id)
        onSuccessRef.current?.("Fornecedor excluído com sucesso!")
        await loadSuppliers(currentPage)
      } catch (err: any) {
        const { message, fieldErrors } = extractError(err)
        setFieldErrors(fieldErrors ?? null)
        onErrorRef.current?.(message)
        throw err
      }
    },
    [currentPage, loadSuppliers],
  )

  const importSuppliers = useCallback(
    async (file: File): Promise<ImportResponse> => {
      setFieldErrors(null)
      try {
        const text = await file.text()
        const suppliersData = JSON.parse(text) as Omit<Supplier, "id">[]
        const response = await supplierService.importSuppliers(suppliersData)

        if (response.errors.length > 0) {
          onErrorRef.current?.(
            `${response.imported} fornecedores importados. ${response.errors.length} erros encontrados.`,
          )
        } else {
          onSuccessRef.current?.(`${response.imported} fornecedores importados com sucesso!`)
        }

        await loadSuppliers(currentPage)
        return response
      } catch (err: any) {
        const { message, fieldErrors } = extractError(err)
        setFieldErrors(fieldErrors ?? null)
        onErrorRef.current?.(message)
        throw err
      }
    },
    [currentPage, loadSuppliers],
  )

  useEffect(() => {
    loadSuppliers(1)
  }, [])

  return {
    suppliers,
    total,
    currentPage,
    loading,
    error,
    fieldErrors,
    loadSuppliers,
    createSupplier,
    updateSupplier,
    deleteSupplier,
    importSuppliers,
  }
}