import { StrictMode } from "react"
import { createRoot } from "react-dom/client"
import App from "./App.tsx"
import "./index.css"

// Error handling for missing root element
const container = document.getElementById("root")
if (!container) {
  throw new Error("Root element not found. Please ensure index.html contains a div with id='root'")
}

// React 19 createRoot API
const root = createRoot(container)
root.render(
  <StrictMode>
    <App />
  </StrictMode>,
)
