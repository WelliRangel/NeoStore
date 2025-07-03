"use client"

import { useState, useCallback } from "react"

interface ToastState {
  message: string
  type: "success" | "error"
}

interface UseToast {
  toast: ToastState | null
  showToast: (message: string, type: "success" | "error") => void
  hideToast: () => void
}

export function useToast(): UseToast {
  const [toast, setToast] = useState<ToastState | null>(null)

  const showToast = useCallback((message: string, type: "success" | "error") => {
    setToast({ message, type })
  }, [])

  const hideToast = useCallback(() => {
    setToast(null)
  }, [])

  return {
    toast,
    showToast,
    hideToast,
  }
}
