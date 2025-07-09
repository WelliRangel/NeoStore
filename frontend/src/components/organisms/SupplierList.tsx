import { memo } from "react"
import type { Supplier } from "@/types/supplier"
import { Card } from "@/components/atoms/Card"
import { Button } from "@/components/atoms/Button"
import { Pagination } from "@/components/molecules/Pagination"
import { LoadingSpinner } from "@/components/atoms/LoadingSpinner"
import { Edit, Trash2, Mail, Building, FileText } from "lucide-react"

interface SupplierListProps {
  suppliers: Supplier[]
  total: number
  currentPage: number
  pageSize: number
  loading: boolean
  onPageChange: (page: number) => void
  onEdit: (supplier: Supplier) => void
  onDelete: (id: number) => void
}

// Memoizar componente para evitar re-renders desnecessários
export const SupplierList = memo<SupplierListProps>(function SupplierList({
  suppliers,
  total,
  currentPage,
  pageSize,
  loading,
  onPageChange,
  onEdit,
  onDelete,
}: SupplierListProps) {
  if (loading) {
    return (
      <Card className="p-8">
        <div className="flex justify-center items-center min-h-[200px]">
          <LoadingSpinner />
        </div>
      </Card>
    )
  }

  if (suppliers.length === 0) {
    return (
      <Card className="p-8">
        <div className="text-center text-gray-500 min-h-[200px] flex flex-col justify-center">
          <Building className="mx-auto h-12 w-12 text-gray-300 mb-4" />
          <p className="text-lg font-medium">Nenhum fornecedor encontrado</p>
          <p className="text-sm mt-2">Clique em "Novo Fornecedor" para adicionar o primeiro</p>
        </div>
      </Card>
    )
  }

  return (
    <div className="space-y-6 w-full">
      {/* Desktop Table View */}
      <div className="hidden lg:block w-full">
        <Card className="overflow-hidden">
          <div className="w-full overflow-x-auto">
            <table className="w-full min-w-[800px]">
              <thead className="bg-gray-50 border-b border-gray-200">
                <tr>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider w-20">
                    ID
                  </th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider min-w-[200px]">
                    Nome
                  </th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider min-w-[250px]">
                    Email
                  </th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider w-40">
                    CNPJ
                  </th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider min-w-[200px]">
                    Descrição
                  </th>
                  <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase tracking-wider w-48">
                    Ações
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-100">
                {suppliers.map((supplier, index) => (
                  <tr
                    key={supplier.id}
                    className={`hover:bg-gray-50 transition-colors ${index % 2 === 0 ? "bg-white" : "bg-gray-25"}`}
                  >
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">#{supplier.id}</td>
                    <td className="px-6 py-4 text-sm font-semibold text-gray-900">
                      <div className="max-w-[200px] truncate" title={supplier.name}>
                        {supplier.name}
                      </div>
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-700">
                      <div className="max-w-[250px] truncate" title={supplier.email}>
                        {supplier.email}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-mono text-gray-700">{supplier.cnpj}</td>
                    <td className="px-6 py-4 text-sm text-gray-700">
                      <div className="max-w-[200px] line-clamp-2" title={supplier.description}>
                        {supplier.description}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm">
                      <div className="flex justify-end gap-2">
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => onEdit(supplier)}
                          className="flex items-center gap-1 hover:bg-blue-50 hover:border-blue-300 hover:text-blue-700"
                          aria-label={`Editar fornecedor ${supplier.name}`}
                        >
                          <Edit className="w-4 h-4" />
                          <span className="hidden xl:inline">Editar</span>
                        </Button>
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => onDelete(supplier.id)}
                          className="flex items-center gap-1 text-red-600 hover:text-red-700 hover:border-red-300 hover:bg-red-50"
                          aria-label={`Excluir fornecedor ${supplier.name}`}
                        >
                          <Trash2 className="w-4 h-4" />
                          <span className="hidden xl:inline">Excluir</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </Card>
      </div>

      {/* Mobile/Tablet Card View */}
      <div className="lg:hidden space-y-4 w-full">
        {suppliers.map((supplier) => (
          <Card key={supplier.id} className="p-4 hover:shadow-md transition-shadow">
            <div className="space-y-3 w-full">
              <div className="flex justify-between items-start">
                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2 mb-2">
                    <span className="text-xs font-medium text-gray-500 bg-gray-100 px-2 py-1 rounded">
                      ID #{supplier.id}
                    </span>
                  </div>
                  <h3 className="text-lg font-semibold text-gray-900 mb-2 break-words">{supplier.name}</h3>
                </div>
              </div>

              <div className="space-y-3">
                <div className="flex items-center gap-3 text-sm">
                  <Mail className="w-4 h-4 text-gray-400 flex-shrink-0" />
                  <span className="text-gray-700 break-all min-w-0 flex-1">{supplier.email}</span>
                </div>

                <div className="flex items-center gap-3 text-sm">
                  <Building className="w-4 h-4 text-gray-400 flex-shrink-0" />
                  <span className="text-gray-700 font-mono">{supplier.cnpj}</span>
                </div>

                {supplier.description && (
                  <div className="flex items-start gap-3 text-sm">
                    <FileText className="w-4 h-4 text-gray-400 flex-shrink-0 mt-0.5" />
                    <span className="text-gray-700 leading-relaxed break-words min-w-0 flex-1">
                      {supplier.description}
                    </span>
                  </div>
                )}
              </div>

              <div className="flex gap-3 pt-4 border-t border-gray-100">
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => onEdit(supplier)}
                  className="flex-1 flex items-center justify-center gap-2 hover:bg-blue-50 hover:border-blue-300 hover:text-blue-700"
                  aria-label={`Editar fornecedor ${supplier.name}`}
                >
                  <Edit className="w-4 h-4" />
                  Editar
                </Button>
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => onDelete(supplier.id)}
                  className="flex-1 flex items-center justify-center gap-2 text-red-600 hover:text-red-700 hover:border-red-300 hover:bg-red-50"
                  aria-label={`Excluir fornecedor ${supplier.name}`}
                >
                  <Trash2 className="w-4 h-4" />
                  Excluir
                </Button>
              </div>
            </div>
          </Card>
        ))}
      </div>

      {/* Pagination and Stats */}
      <div className="flex flex-col sm:flex-row justify-between items-center gap-4 pt-4 w-full">
        <p className="text-sm text-gray-600 order-2 sm:order-1">
          Mostrando <span className="font-medium">{(currentPage - 1) * pageSize + 1}</span> a{" "}
          <span className="font-medium">{Math.min(currentPage * pageSize, total)}</span> de{" "}
          <span className="font-medium">{total}</span> fornecedores
        </p>
        <div className="order-1 sm:order-2">
          <Pagination currentPage={currentPage} totalPages={Math.ceil(total / pageSize)} onPageChange={onPageChange} />
        </div>
      </div>
    </div>
  )
})
