import { defineConfig } from "vite"
import react from "@vitejs/plugin-react"
import path from "node:path"

export default defineConfig({
  plugins: [
    react({
      // React 19 optimizations
      jsxRuntime: "automatic",
      jsxImportSource: "react",
      // Enable Fast Refresh
      fastRefresh: true,
    }),
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
      "@/components": path.resolve(__dirname, "./src/components"),
      "@/hooks": path.resolve(__dirname, "./src/hooks"),
      "@/services": path.resolve(__dirname, "./src/services"),
      "@/types": path.resolve(__dirname, "./src/types"),
      "@/utils": path.resolve(__dirname, "./src/utils"),
    },
  },
  server: {
    port: 3000,
    open: true,
    host: true,
    cors: true,
    strictPort: false,
    hmr: {
      overlay: true,
    },
  },
  build: {
    outDir: "dist",
    sourcemap: true,
    target: "es2022",
    minify: "esbuild",
    cssMinify: true,
    rollupOptions: {
      output: {
        manualChunks: {
          vendor: ["react", "react-dom"],
          icons: ["lucide-react"],
          utils: ["./src/utils/validation", "./src/utils/formatters"],
        },
        chunkFileNames: "assets/js/[name]-[hash].js",
        entryFileNames: "assets/js/[name]-[hash].js",
        assetFileNames: "assets/[ext]/[name]-[hash].[ext]",
      },
    },
    chunkSizeWarningLimit: 1000,
  },
  optimizeDeps: {
    include: ["react", "react-dom", "lucide-react"],
    exclude: ["@vite/client", "@vite/env"],
    esbuildOptions: {
      target: "es2022",
    },
  },
  define: {
    __DEV__: JSON.stringify(process.env.NODE_ENV === "development"),
  },
  css: {
    devSourcemap: true,
    modules: {
      localsConvention: "camelCase",
    },
  },
  esbuild: {
    target: "es2022",
    logOverride: { "this-is-undefined-in-esm": "silent" },
  },
  preview: {
    port: 3000,
    open: true,
    cors: true,
  },
})
