"use client"

import { useState, useEffect } from "react"
import { SuppliersPage } from "@/components/pages/SuppliersPage"
import type { Supplier } from "@/types/supplier"
import { supplierService } from "@/services/supplierService"
import { Toast } from "@/components/atoms/Toast"

function App() {
  const [suppliers, setSuppliers] = useState<Supplier[]>([])
  const [total, setTotal] = useState(0)
  const [currentPage, setCurrentPage] = useState(1)
  const [loading, setLoading] = useState(false)
  const [toast, setToast] = useState<{ message: string; type: "success" | "error" } | null>(null)

  const pageSize = 5

  const loadSuppliers = async (page = 1) => {
    setLoading(true)
    try {
      const response = await supplierService.getSuppliers(page, pageSize)
      setSuppliers(response.data)
      setTotal(response.total)
      setCurrentPage(page)
    } catch (error) {
      showToast("Erro ao carregar fornecedores", "error")
    } finally {
      setLoading(false)
    }
  }

  const showToast = (message: string, type: "success" | "error") => {
    setToast({ message, type })
    setTimeout(() => setToast(null), 5000)
  }

  const handleCreateSupplier = async (supplierData: Omit<Supplier, "id">) => {
    try {
      await supplierService.createSupplier(supplierData)
      showToast("Fornecedor criado com sucesso!", "success")
      loadSuppliers(currentPage)
    } catch (error: any) {
      showToast(error.message || "Erro ao criar fornecedor", "error")
    }
  }

  const handleUpdateSupplier = async (id: number, supplierData: Omit<Supplier, "id">) => {
    try {
      await supplierService.updateSupplier(id, supplierData)
      showToast("Fornecedor atualizado com sucesso!", "success")
      loadSuppliers(currentPage)
    } catch (error: any) {
      showToast(error.message || "Erro ao atualizar fornecedor", "error")
    }
  }

  const handleDeleteSupplier = async (id: number) => {
    if (window.confirm("Tem certeza que deseja excluir este fornecedor?")) {
      try {
        await supplierService.deleteSupplier(id)
        showToast("Fornecedor excluído com sucesso!", "success")
        loadSuppliers(currentPage)
      } catch (error: any) {
        showToast(error.message || "Erro ao excluir fornecedor", "error")
      }
    }
  }

  const handleImportSuppliers = async (file: File) => {
    try {
      const text = await file.text()
      const suppliersData = JSON.parse(text)
      const response = await supplierService.importSuppliers(suppliersData)

      if (response.errors.length > 0) {
        showToast(`${response.imported} fornecedores importados. ${response.errors.length} erros encontrados.`, "error")
      } else {
        showToast(`${response.imported} fornecedores importados com sucesso!`, "success")
      }

      loadSuppliers(currentPage)
      return response
    } catch (error: any) {
      showToast(error.message || "Erro ao importar fornecedores", "error")
      throw error
    }
  }

  useEffect(() => {
    loadSuppliers()
  }, [])

  return (
    <div className="min-h-screen bg-gray-50">
      <SuppliersPage
        suppliers={suppliers}
        total={total}
        currentPage={currentPage}
        pageSize={pageSize}
        loading={loading}
        onPageChange={loadSuppliers}
        onCreateSupplier={handleCreateSupplier}
        onUpdateSupplier={handleUpdateSupplier}
        onDeleteSupplier={handleDeleteSupplier}
        onImportSuppliers={handleImportSuppliers}
      />
      {toast && <Toast message={toast.message} type={toast.type} onClose={() => setToast(null)} />}
    </div>
  )
}

export default App
