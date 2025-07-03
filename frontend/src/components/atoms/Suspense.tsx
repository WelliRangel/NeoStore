import { Suspense as ReactSuspense, type ReactNode } from "react"
import { LoadingSpinner } from "./LoadingSpinner"
import { Card } from "./Card"

interface SuspenseProps {
  children: ReactNode
  fallback?: ReactNode
}

export function Suspense({ children, fallback }: SuspenseProps) {
  const defaultFallback = (
    <Card className="p-8">
      <div className="flex justify-center items-center min-h-[200px]">
        <LoadingSpinner />
      </div>
    </Card>
  )

  return <ReactSuspense fallback={fallback || defaultFallback}>{children}</ReactSuspense>
}
