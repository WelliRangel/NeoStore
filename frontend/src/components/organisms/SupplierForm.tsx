"use client"

import type { FormEvent, ChangeEvent } from "react"
import { useState, useEffect, useCallback, useId } from "react"
import type { Supplier } from "@/types/supplier"
import { Modal } from "@/components/molecules/Modal"
import { FormField } from "@/components/molecules/FormField"
import { Button } from "@/components/atoms/Button"
import { validateSupplier } from "@/utils/validation"
import { formatCNPJ } from "@/utils/formatters"

interface SupplierFormProps {
  supplier?: Supplier | null
  onSubmit: (supplier: Omit<Supplier, "id">) => Promise<void>
  onClose: () => void
}

interface FormData {
  name: string
  email: string
  description: string
  cnpj: string
}

export function SupplierForm({ supplier, onSubmit, onClose }: SupplierFormProps) {
  const formId = useId()
  const [formData, setFormData] = useState<FormData>({
    name: "",
    email: "",
    description: "",
    cnpj: "",
  })
  const [errors, setErrors] = useState<Record<string, string>>({})
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    if (supplier) {
      setFormData({
        name: supplier.name,
        email: supplier.email,
        description: supplier.description,
        cnpj: supplier.cnpj,
      })
    }
  }, [supplier])

  const handleChange = useCallback(
    (field: keyof FormData, value: string) => {
      let processedValue = value

      if (field === "cnpj") {
        processedValue = formatCNPJ(value)
      }

      setFormData((prev) => ({ ...prev, [field]: processedValue }))

      // Clear error when user starts typing
      if (errors[field]) {
        setErrors((prev) => ({ ...prev, [field]: "" }))
      }
    },
    [errors],
  )

  const handleInputChange = useCallback(
    (field: keyof FormData) => (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      handleChange(field, e.target.value)
    },
    [handleChange],
  )

  const handleSubmit = useCallback(
    async (e: FormEvent<HTMLFormElement>) => {
      e.preventDefault()

      const validationErrors = validateSupplier(formData)
      if (Object.keys(validationErrors).length > 0) {
        setErrors(validationErrors)
        // Focus on first error field
        const firstErrorField = Object.keys(validationErrors)[0]
        const element = document.querySelector(`[name="${firstErrorField}"]`) as HTMLElement
        element?.focus()
        return
      }

      setLoading(true)
      try {
        await onSubmit(formData)
      } catch (error) {
        // Error handling is done in parent component
        console.error("Form submission error:", error)
      } finally {
        setLoading(false)
      }
    },
    [formData, onSubmit],
  )

  return (
    <Modal isOpen={true} onClose={onClose} title={supplier ? "Editar Fornecedor" : "Novo Fornecedor"} size="lg">
      <form id={formId} onSubmit={handleSubmit} className="space-y-6 w-full" noValidate>
        <div className="grid grid-cols-1 gap-6">
          <FormField label="Nome" required error={errors.name}>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleInputChange("name")}
              className={`
                w-full px-4 py-3 border rounded-lg 
                focus:outline-none focus:ring-2 focus:ring-ring focus:border-transparent 
                transition-colors text-base
                ${errors.name ? "border-destructive bg-destructive/5" : "border-border hover:border-border/80"}
              `}
              placeholder="Nome do fornecedor"
              aria-describedby={errors.name ? "name-error" : undefined}
              autoComplete="organization"
              maxLength={100}
            />
          </FormField>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <FormField label="Email" required error={errors.email}>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange("email")}
                className={`
                  w-full px-4 py-3 border rounded-lg 
                  focus:outline-none focus:ring-2 focus:ring-ring focus:border-transparent 
                  transition-colors text-base
                  ${errors.email ? "border-destructive bg-destructive/5" : "border-border hover:border-border/80"}
                `}
                placeholder="email@exemplo.com"
                aria-describedby={errors.email ? "email-error" : undefined}
                autoComplete="email"
                maxLength={100}
              />
            </FormField>

            <FormField label="CNPJ" required error={errors.cnpj}>
              <input
                type="text"
                name="cnpj"
                value={formData.cnpj}
                onChange={handleInputChange("cnpj")}
                className={`
                  w-full px-4 py-3 border rounded-lg 
                  focus:outline-none focus:ring-2 focus:ring-ring focus:border-transparent 
                  transition-colors font-mono text-base
                  ${errors.cnpj ? "border-destructive bg-destructive/5" : "border-border hover:border-border/80"}
                `}
                placeholder="00.000.000/0001-00"
                maxLength={18}
                aria-describedby={errors.cnpj ? "cnpj-error" : undefined}
                inputMode="numeric"
              />
            </FormField>
          </div>

          <FormField label="Descrição" error={errors.description}>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleInputChange("description")}
              className={`
                w-full px-4 py-3 border rounded-lg 
                focus:outline-none focus:ring-2 focus:ring-ring focus:border-transparent 
                transition-colors resize-none text-base
                ${errors.description ? "border-destructive bg-destructive/5" : "border-border hover:border-border/80"}
              `}
              placeholder="Descrição do fornecedor (obrigatório)"
              rows={4}
              aria-describedby={errors.description ? "description-error" : undefined}
              maxLength={500}
            />
          </FormField>
        </div>

        <div className="flex flex-col sm:flex-row justify-end gap-3 pt-6 border-t border-border">
          <Button
            type="button"
            variant="outline"
            onClick={onClose}
            disabled={loading}
            className="w-full sm:w-auto order-2 sm:order-1 bg-transparent"
          >
            Cancelar
          </Button>
          <Button type="submit" loading={loading} className="w-full sm:w-auto order-1 sm:order-2">
            {supplier ? "Atualizar" : "Criar"} Fornecedor
          </Button>
        </div>
      </form>
    </Modal>
  )
}
