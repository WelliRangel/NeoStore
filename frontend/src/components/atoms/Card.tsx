import type { ReactNode } from "react"

interface CardProps {
  children: ReactNode
  className?: string
}

export function Card({ children, className = "" }: CardProps) {
  return (
    <div
      className={`
        bg-white rounded-lg shadow-sm border border-gray-200
        w-full h-auto
        min-h-fit
        flex flex-col
        ${className}
      `}
    >
      {children}
    </div>
  )
}
