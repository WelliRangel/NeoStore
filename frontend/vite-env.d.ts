/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_API_BASE_URL?: string
  readonly VITE_APP_TITLE?: string
  readonly VITE_APP_VERSION?: string
  readonly MODE: string
  readonly BASE_URL: string
  readonly PROD: boolean
  readonly DEV: boolean
  readonly SSR: boolean
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}

// Extend Window interface for global variables
declare global {
  interface Window {
    __DEV__?: boolean
  }

  // Node.js process for development checks
  namespace NodeJS {
    interface ProcessEnv {
      NODE_ENV: "development" | "production" | "test"
    }
  }
}

// Module declarations for assets
declare module "*.svg" {
  import type { FunctionComponent, SVGProps } from "react"
  const content: FunctionComponent<SVGProps<SVGSVGElement>>
  export default content
}

declare module "*.png" {
  const content: string
  export default content
}

declare module "*.jpg" {
  const content: string
  export default content
}

declare module "*.jpeg" {
  const content: string
  export default content
}

declare module "*.gif" {
  const content: string
  export default content
}

declare module "*.webp" {
  const content: string
  export default content
}

declare module "*.ico" {
  const content: string
  export default content
}

declare module "*.bmp" {
  const content: string
  export default content
}

// CSS Modules
declare module "*.module.css" {
  const classes: { readonly [key: string]: string }
  export default classes
}

declare module "*.module.scss" {
  const classes: { readonly [key: string]: string }
  export default classes
}

declare module "*.module.sass" {
  const classes: { readonly [key: string]: string }
  export default classes
}

// JSON files
declare module "*.json" {
  const content: any
  export default content
}

export {}
