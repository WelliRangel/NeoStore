"use client"

import { useState, useCallback } from "react"
import { Layout } from "@/components/templates/Layout"
import { SupplierList } from "@/components/organisms/SupplierList"
import { SupplierForm } from "@/components/organisms/SupplierForm"
import { ImportModal } from "@/components/organisms/ImportModal"
import { Button } from "@/components/atoms/Button"
import type { Supplier, ImportResponse } from "@/types/supplier"
import { Plus, Upload, Building2 } from "lucide-react"

interface SuppliersPageProps {
  suppliers: Supplier[]
  total: number
  currentPage: number
  pageSize: number
  loading: boolean
  onPageChange: (page: number) => void
  onCreateSupplier: (supplier: Omit<Supplier, "id">) => Promise<void>
  onUpdateSupplier: (id: number, supplier: Omit<Supplier, "id">) => Promise<void>
  onDeleteSupplier: (id: number) => Promise<void>
  onImportSuppliers: (file: File) => Promise<ImportResponse>
}

export function SuppliersPage({
  suppliers,
  total,
  currentPage,
  pageSize,
  loading,
  onPageChange,
  onCreateSupplier,
  onUpdateSupplier,
  onDeleteSupplier,
  onImportSuppliers,
}: SuppliersPageProps) {
  const [showForm, setShowForm] = useState(false)
  const [showImport, setShowImport] = useState(false)
  const [editingSupplier, setEditingSupplier] = useState<Supplier | null>(null)

  // Adicionar useCallback para handlers
  const handleEdit = useCallback((supplier: Supplier) => {
    setEditingSupplier(supplier)
    setShowForm(true)
  }, [])

  const handleCloseForm = useCallback(() => {
    setShowForm(false)
    setEditingSupplier(null)
  }, [])

  const handleSubmitForm = useCallback(
    async (supplierData: Omit<Supplier, "id">) => {
      if (editingSupplier) {
        await onUpdateSupplier(editingSupplier.id, supplierData)
      } else {
        await onCreateSupplier(supplierData)
      }
      handleCloseForm()
    },
    [editingSupplier, onUpdateSupplier, onCreateSupplier, handleCloseForm],
  )

  return (
    <Layout>
      <div className="space-y-6 sm:space-y-8">
        {/* Header Section */}
        <div className="flex flex-col space-y-4 sm:space-y-0 sm:flex-row sm:justify-between sm:items-start">
          <div className="flex items-start gap-3">
            <div className="flex items-center justify-center w-12 h-12 bg-accent rounded-xl shadow-elegant">
              <Building2 className="w-6 h-6 text-accent-foreground" />
            </div>
            <div>
              <h1 className="text-2xl sm:text-3xl font-bold text-foreground">Fornecedores</h1>
              <p className="text-muted-foreground mt-1 text-sm sm:text-base">Gerencie os fornecedores da Neostore</p>
              {total > 0 && (
                <p className="text-xs text-muted-foreground mt-1">
                  {total} fornecedor{total !== 1 ? "es" : ""} cadastrado{total !== 1 ? "s" : ""}
                </p>
              )}
            </div>
          </div>

          <div className="flex flex-col sm:flex-row gap-3 w-full sm:w-auto">
            <Button
              onClick={() => setShowImport(true)}
              variant="outline"
              className="flex items-center justify-center gap-2 w-full sm:w-auto order-2 sm:order-1"
            >
              <Upload className="w-4 h-4" />
              <span>Importar</span>
            </Button>
            <Button
              onClick={() => setShowForm(true)}
              className="flex items-center justify-center gap-2 w-full sm:w-auto order-1 sm:order-2"
            >
              <Plus className="w-4 h-4" />
              <span>Novo Fornecedor</span>
            </Button>
          </div>
        </div>

        {/* Content Section */}
        <SupplierList
          suppliers={suppliers}
          total={total}
          currentPage={currentPage}
          pageSize={pageSize}
          loading={loading}
          onPageChange={onPageChange}
          onEdit={handleEdit}
          onDelete={onDeleteSupplier}
        />

        {/* Modals */}
        {showForm && <SupplierForm supplier={editingSupplier} onSubmit={handleSubmitForm} onClose={handleCloseForm} />}

        {showImport && <ImportModal onImport={onImportSuppliers} onClose={() => setShowImport(false)} />}
      </div>
    </Layout>
  )
}
