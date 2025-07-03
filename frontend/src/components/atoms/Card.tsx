import type { ReactNode } from "react"

interface CardProps {
  children: ReactNode
  className?: string
}

export function Card({ children, className = "" }: CardProps) {
  return (
    <div
      className={`
        bg-card text-card-foreground rounded-lg border border-border
        shadow-elegant hover:shadow-elegant-md transition-elegant
        w-full h-auto min-h-fit flex flex-col
        ${className}
      `}
    >
      {children}
    </div>
  )
}
