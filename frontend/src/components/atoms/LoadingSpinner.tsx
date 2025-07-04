interface LoadingSpinnerProps {
  className?: string
}

export function LoadingSpinner({ className = "w-8 h-8" }: LoadingSpinnerProps) {
  return (
    <div
      className={`animate-spin rounded-full border-2 border-muted border-t-primary ${className}`}
      role="status"
      aria-label="Carregando"
    />
  )
}
