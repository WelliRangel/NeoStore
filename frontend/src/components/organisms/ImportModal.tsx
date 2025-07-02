"use client"

import type React from "react"
import { useState } from "react"
import { Modal } from "@/components/molecules/Modal"
import { Button } from "@/components/atoms/Button"
import { Card } from "@/components/atoms/Card"
import type { ImportResponse } from "@/types/supplier"
import { Upload, FileText, AlertCircle, CheckCircle } from "lucide-react"

interface ImportModalProps {
  onImport: (file: File) => Promise<ImportResponse>
  onClose: () => void
}

export function ImportModal({ onImport, onClose }: ImportModalProps) {
  const [file, setFile] = useState<File | null>(null)
  const [loading, setLoading] = useState(false)
  const [result, setResult] = useState<ImportResponse | null>(null)

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = e.target.files?.[0]
    if (selectedFile && selectedFile.type === "application/json") {
      setFile(selectedFile)
      setResult(null)
    } else {
      alert("Por favor, selecione um arquivo JSON válido.")
    }
  }

  const handleImport = async () => {
    if (!file) return

    setLoading(true)
    try {
      const response = await onImport(file)
      setResult(response)
    } catch (error) {
      // Error handling is done in parent component
    } finally {
      setLoading(false)
    }
  }

  const handleClose = () => {
    setFile(null)
    setResult(null)
    onClose()
  }

  return (
    <Modal isOpen={true} onClose={handleClose} title="Importar Fornecedores" size="lg">
      <div className="space-y-6 w-full">
        {!result ? (
          <>
            <label
              htmlFor="file-upload"
              className="
                block border-2 border-dashed border-gray-300 rounded-lg p-8 
                cursor-pointer hover:border-gray-400 hover:bg-gray-50 
                transition-colors w-full min-h-[200px]
                flex items-center justify-center
              "
            >
              <div className="text-center w-full">
                <Upload className="mx-auto h-12 w-12 text-gray-400 mb-4" />
                <div className="space-y-2">
                  <span className="block text-base font-medium text-gray-900">Selecione um arquivo JSON</span>
                  <input
                    id="file-upload"
                    name="file-upload"
                    type="file"
                    accept=".json"
                    className="sr-only"
                    onChange={handleFileChange}
                  />
                  <p className="text-sm text-gray-500">Arquivo deve conter um array de fornecedores</p>
                </div>
              </div>
            </label>

            {file && (
              <Card className="p-4">
                <div className="flex items-center gap-3">
                  <FileText className="h-8 w-8 text-blue-500 flex-shrink-0" />
                  <div className="min-w-0 flex-1">
                    <p className="font-medium truncate">{file.name}</p>
                    <p className="text-sm text-gray-500">{(file.size / 1024).toFixed(2)} KB</p>
                  </div>
                </div>
              </Card>
            )}

            <div className="bg-blue-50 p-4 rounded-lg">
              <h4 className="font-medium text-blue-900 mb-2">Formato esperado:</h4>
              <pre className="text-xs text-blue-800 bg-blue-100 p-3 rounded overflow-x-auto whitespace-pre-wrap">
                {`[
  {
    "name": "Fornecedor A",
    "email": "a@email.com",
    "description": "Equipamentos",
    "cnpj": "00.000.000/0001-00"
  }
]`}
              </pre>
            </div>

            <div className="flex flex-col sm:flex-row justify-end gap-3">
              <Button
                variant="outline"
                onClick={handleClose}
                className="w-full sm:w-auto order-2 sm:order-1 bg-transparent"
              >
                Cancelar
              </Button>
              <Button
                onClick={handleImport}
                disabled={!file}
                loading={loading}
                className="w-full sm:w-auto order-1 sm:order-2"
              >
                Importar
              </Button>
            </div>
          </>
        ) : (
          <>
            <div className="text-center py-8">
              <CheckCircle className="mx-auto h-16 w-16 text-green-500 mb-4" />
              <h3 className="text-xl font-medium mb-2">Importação Concluída</h3>
              <p className="text-gray-600">{result.imported} fornecedores importados com sucesso</p>
            </div>

            {result.errors.length > 0 && (
              <Card className="p-0">
                <div className="p-4">
                  <div className="flex items-center gap-2 mb-4">
                    <AlertCircle className="h-5 w-5 text-red-500" />
                    <h4 className="font-medium text-red-900">Erros encontrados ({result.errors.length})</h4>
                  </div>
                  <div className="space-y-3 max-h-60 overflow-y-auto">
                    {result.errors.map((error, index) => (
                      <div key={index} className="bg-red-50 p-3 rounded border-l-4 border-red-400">
                        <p className="text-sm font-medium text-red-800">
                          Linha {error.index + 1}: {error.error}
                        </p>
                        <p className="text-xs text-red-600 mt-1">
                          Fornecedor: {error.supplier.name} ({error.supplier.email})
                        </p>
                      </div>
                    ))}
                  </div>
                </div>
              </Card>
            )}

            <div className="flex justify-end">
              <Button onClick={handleClose} className="w-full sm:w-auto">
                Fechar
              </Button>
            </div>
          </>
        )}
      </div>
    </Modal>
  )
}
