import type { ReactNode, ButtonHTMLAttributes } from "react"
import { LoadingSpinner } from "./LoadingSpinner"

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  children: ReactNode
  variant?: "primary" | "outline" | "ghost"
  size?: "sm" | "md" | "lg"
  loading?: boolean
}

export function Button({
  children,
  variant = "primary",
  size = "md",
  loading = false,
  disabled,
  className = "",
  ...props
}: ButtonProps) {
  const baseClasses =
    "inline-flex items-center justify-center font-medium rounded-lg focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 transition-elegant disabled:cursor-not-allowed"

  const variantClasses = {
    primary:
      "bg-primary text-primary-foreground hover:bg-primary/90 focus:ring-primary/20 disabled:bg-primary/50 shadow-elegant hover:shadow-elegant-md",
    outline:
      "border border-border bg-background text-foreground hover:bg-accent hover:text-accent-foreground focus:ring-primary/20 disabled:bg-muted disabled:text-muted-foreground shadow-elegant hover:shadow-elegant-md hover:border-primary/20",
    ghost:
      "text-foreground hover:bg-accent hover:text-accent-foreground focus:ring-primary/20 disabled:text-muted-foreground",
  }

  const sizeClasses = {
    sm: "px-3 py-2 text-sm min-h-[36px]",
    md: "px-4 py-2.5 text-sm min-h-[40px]",
    lg: "px-6 py-3 text-base min-h-[44px]",
  }

  const isDisabled = disabled || loading

  return (
    <button
      className={`${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${isDisabled ? "opacity-60" : ""} ${className}`}
      disabled={isDisabled}
      aria-disabled={isDisabled}
      {...props}
    >
      {loading && <LoadingSpinner className="w-4 h-4 mr-2" />}
      <span className={loading ? "opacity-75" : ""}>{children}</span>
    </button>
  )
}
