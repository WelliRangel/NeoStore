import { Component, type ReactNode, type ErrorInfo } from "react"
import { Card } from "@/components/atoms/Card"
import { Button } from "@/components/atoms/Button"
import { AlertTriangle, RefreshCw } from "lucide-react"

interface Props {
  children: ReactNode
  fallback?: ReactNode
}

interface State {
  hasError: boolean
  error?: Error
}

export class ErrorBoundary extends Component<Props, State> {
  constructor(props: Props) {
    super(props)
    this.state = { hasError: false }
  }

  static getDerivedStateFromError(error: Error): State {
    return { hasError: true, error }
  }

  override componentDidCatch(error: Error, errorInfo: ErrorInfo) {
    // Log to error reporting service in production
    if (import.meta.env.PROD) {
      // Example: errorReportingService.captureException(error, { extra: errorInfo })
      console.error("Production error:", error, errorInfo)
    } else {
      console.error("ErrorBoundary caught an error:", error, errorInfo)
    }
  }

  private handleReset = () => {
    this.setState({ hasError: false })
  }

  override render() {
    if (this.state.hasError) {
      if (this.props.fallback) {
        return this.props.fallback
      }

      return (
        <div className="min-h-screen bg-background flex items-center justify-center p-4">
          <Card className="max-w-md w-full p-6 text-center">
            <AlertTriangle className="w-12 h-12 text-destructive mx-auto mb-4" />
            <h2 className="text-xl font-semibold text-foreground mb-2">Algo deu errado</h2>
            <p className="text-muted-foreground mb-6">Ocorreu um erro inesperado. Tente recarregar a página.</p>
            <div className="space-y-3">
              <Button onClick={this.handleReset} className="w-full">
                <RefreshCw className="w-4 h-4 mr-2" />
                Tentar novamente
              </Button>
              <Button variant="outline" onClick={() => window.location.reload()} className="w-full">
                Recarregar página
              </Button>
            </div>
            {import.meta.env.DEV && this.state.error && (
              <details className="mt-4 text-left">
                <summary className="cursor-pointer text-sm text-muted-foreground">Detalhes do erro</summary>
                <pre className="mt-2 text-xs bg-muted p-2 rounded overflow-auto max-h-32">{this.state.error.stack}</pre>
              </details>
            )}
          </Card>
        </div>
      )
    }

    return this.props.children
  }
}
