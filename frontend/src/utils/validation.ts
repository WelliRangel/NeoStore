export function validateSupplier(supplier: {
  name: string
  email: string
  description: string
  cnpj: string
}): Record<string, string> {
  const errors: Record<string, string> = {}

  // Nome obrigatório
  if (!supplier.name.trim()) {
    errors.name = "Nome é obrigatório"
  }

  // Email obrigatório e formato válido
  if (!supplier.email.trim()) {
    errors.email = "Email é obrigatório"
  } else if (!isValidEmail(supplier.email)) {
    errors.email = "Email deve ter um formato válido"
  }

  // CNPJ obrigatório e formato válido
  if (!supplier.cnpj.trim()) {
    errors.cnpj = "CNPJ é obrigatório"
  } else if (!isValidCNPJ(supplier.cnpj)) {
    errors.cnpj = "CNPJ deve ter um formato válido"
  }

  return errors
}

function isValidEmail(email: string): boolean {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return emailRegex.test(email)
}

function isValidCNPJ(cnpj: string): boolean {
  // Remove formatação
  const cleanCNPJ = cnpj.replace(/[^\d]/g, "")

  // Verifica se tem 14 dígitos
  if (cleanCNPJ.length !== 14) return false

  // Verifica se não são todos iguais
  if (/^(\d)\1+$/.test(cleanCNPJ)) return false

  // Validação dos dígitos verificadores
  let sum = 0
  let weight = 5

  // Primeiro dígito verificador
  for (let i = 0; i < 12; i++) {
    const digit = cleanCNPJ.charAt(i)
    if (!digit) return false // Type guard
    sum += Number.parseInt(digit, 10) * weight
    weight = weight === 2 ? 9 : weight - 1
  }

  let remainder = sum % 11
  const firstDigit = remainder < 2 ? 0 : 11 - remainder

  const digit12 = cleanCNPJ.charAt(12)
  if (!digit12 || Number.parseInt(digit12, 10) !== firstDigit) return false

  // Segundo dígito verificador
  sum = 0
  weight = 6

  for (let i = 0; i < 13; i++) {
    const digit = cleanCNPJ.charAt(i)
    if (!digit) return false // Type guard
    sum += Number.parseInt(digit, 10) * weight
    weight = weight === 2 ? 9 : weight - 1
  }

  remainder = sum % 11
  const secondDigit = remainder < 2 ? 0 : 11 - remainder

  const digit13 = cleanCNPJ.charAt(13)
  return digit13 ? Number.parseInt(digit13, 10) === secondDigit : false
}
