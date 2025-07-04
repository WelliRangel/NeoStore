export const API_CONFIG = {
  // Use direct API URL since backend now has CORS configured
  BASE_URL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/neostore/api/v1",
  TIMEOUT: 10000,
  RETRY_ATTEMPTS: 3,
} as const

export const PAGINATION = {
  DEFAULT_PAGE_SIZE: 5,
  MAX_PAGE_SIZE: 100,
} as const

export const VALIDATION = {
  MAX_NAME_LENGTH: 100,
  MAX_DESCRIPTION_LENGTH: 500,
  MAX_EMAIL_LENGTH: 100,
} as const

export const FILE_UPLOAD = {
  MAX_SIZE: 5 * 1024 * 1024, // 5MB
  ALLOWED_TYPES: ["application/json"],
} as const
