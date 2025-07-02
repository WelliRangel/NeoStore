"use client"

import { Button } from "@/components/atoms/Button"
import { ChevronLeft, ChevronRight } from "lucide-react"

interface PaginationProps {
  currentPage: number
  totalPages: number
  onPageChange: (page: number) => void
}

export function Pagination({ currentPage, totalPages, onPageChange }: PaginationProps) {
  const canGoPrevious = currentPage > 1
  const canGoNext = currentPage < totalPages

  if (totalPages <= 1) return null

  return (
    <nav className="flex items-center gap-2" aria-label="Paginação">
      <Button
        variant="outline"
        size="sm"
        onClick={() => onPageChange(currentPage - 1)}
        disabled={!canGoPrevious}
        className="flex items-center gap-1 min-w-[100px] justify-center"
        aria-label="Página anterior"
      >
        <ChevronLeft className="w-4 h-4" />
        <span className="hidden sm:inline">Anterior</span>
      </Button>

      <div className="flex items-center px-3 py-2 text-sm text-gray-700 bg-gray-50 rounded-lg border min-w-[120px] justify-center">
        <span className="font-medium">{currentPage}</span>
        <span className="mx-1 text-gray-400">/</span>
        <span>{totalPages}</span>
      </div>

      <Button
        variant="outline"
        size="sm"
        onClick={() => onPageChange(currentPage + 1)}
        disabled={!canGoNext}
        className="flex items-center gap-1 min-w-[100px] justify-center"
        aria-label="Próxima página"
      >
        <span className="hidden sm:inline">Próximo</span>
        <ChevronRight className="w-4 h-4" />
      </Button>
    </nav>
  )
}
