"use client"

import { useEffect } from "react"
import { CheckCircle, XCircle, X } from "lucide-react"

interface ToastProps {
  message: string
  type: "success" | "error"
  onClose: () => void
}

export function Toast({ message, type, onClose }: ToastProps) {
  useEffect(() => {
    const timer = setTimeout(onClose, 5000)
    return () => clearTimeout(timer)
  }, [onClose])

  const bgColor = type === "success" ? "bg-success/10 border-success/20" : "bg-destructive/10 border-destructive/20"
  const textColor = type === "success" ? "text-success-foreground" : "text-destructive-foreground"
  const Icon = type === "success" ? CheckCircle : XCircle
  const iconColor = type === "success" ? "text-success" : "text-destructive"

  return (
    <div
      className="fixed top-4 right-4 left-4 sm:left-auto z-50 max-w-sm mx-auto sm:mx-0"
      role="alert"
      aria-live="polite"
    >
      <div className={`w-full ${bgColor} border rounded-lg shadow-elegant-lg p-4 animate-slide-up backdrop-blur-sm`}>
        <div className="flex items-start gap-3">
          <Icon className={`w-5 h-5 ${iconColor} mt-0.5 flex-shrink-0`} aria-hidden="true" />
          <div className="flex-1 min-w-0">
            <p className={`text-sm font-medium ${textColor} break-words`}>{message}</p>
          </div>
          <button
            onClick={onClose}
            className={`flex-shrink-0 ${textColor} hover:opacity-75 focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 rounded p-1 transition-elegant`}
            aria-label="Fechar notificação"
          >
            <X className="w-4 h-4" />
          </button>
        </div>
      </div>
    </div>
  )
}
