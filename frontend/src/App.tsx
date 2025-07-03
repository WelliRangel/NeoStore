"use client"

import { useMemo } from "react"
import { ErrorBoundary } from "@/components/molecules/ErrorBoundary"
import { Suspense } from "@/components/atoms/Suspense"
import { SuppliersPage } from "@/components/pages/SuppliersPage"
import { Toast } from "@/components/atoms/Toast"
import { useSuppliers } from "@/hooks/useSuppliers"
import { useToast } from "@/hooks/useToast"

function App() {
  const { toast, showToast, hideToast } = useToast()

  // Memoize callbacks to prevent unnecessary re-renders
  const callbacks = useMemo(
    () => ({
      onSuccess: (message: string) => showToast(message, "success"),
      onError: (message: string) => showToast(message, "error"),
    }),
    [showToast],
  )

  const {
    suppliers,
    total,
    currentPage,
    loading,
    loadSuppliers,
    createSupplier,
    updateSupplier,
    deleteSupplier,
    importSuppliers,
  } = useSuppliers({
    pageSize: 5,
    ...callbacks,
  })

  return (
    <ErrorBoundary>
      <div className="min-h-screen bg-background">
        <Suspense>
          <SuppliersPage
            suppliers={suppliers}
            total={total}
            currentPage={currentPage}
            pageSize={5}
            loading={loading}
            onPageChange={loadSuppliers}
            onCreateSupplier={createSupplier}
            onUpdateSupplier={updateSupplier}
            onDeleteSupplier={deleteSupplier}
            onImportSuppliers={importSuppliers}
          />
        </Suspense>
        {toast && <Toast message={toast.message} type={toast.type} onClose={hideToast} />}
      </div>
    </ErrorBoundary>
  )
}

export default App
