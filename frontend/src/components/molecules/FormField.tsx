import type { ReactNode } from "react"
import { AlertCircle } from "lucide-react"

interface FormFieldProps {
  label: string
  children: ReactNode
  error?: string | undefined
  required?: boolean
  hint?: string
}

export function FormField({ label, children, error, required, hint }: FormFieldProps) {
  const fieldId = label.toLowerCase().replace(/\s+/g, "-")

  return (
    <div className="space-y-2">
      <label htmlFor={fieldId} className="block text-sm font-semibold text-gray-700">
        {label}
        {required && (
          <span className="text-red-500 ml-1" aria-label="obrigatório">
            *
          </span>
        )}
      </label>

      {hint && <p className="text-xs text-gray-500 -mt-1">{hint}</p>}

      <div className="relative">
        {children}
        {error && (
          <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
            <AlertCircle className="h-5 w-5 text-red-400" aria-hidden="true" />
          </div>
        )}
      </div>

      {error && (
        <p className="text-sm text-red-600 flex items-center gap-1" id={`${fieldId}-error`} role="alert">
          <span>{error}</span>
        </p>
      )}
    </div>
  )
}
