export function formatCNPJ(value: string): string {
  // Remove tudo que não é dígito
  const cleanValue = value.replace(/\D/g, "")

  // Aplica a máscara
  return cleanValue
    .replace(/^(\d{2})(\d)/, "$1.$2")
    .replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3")
    .replace(/\.(\d{3})(\d)/, ".$1/$2")
    .replace(/(\d{4})(\d)/, "$1-$2")
    .substring(0, 18) // Limita o tamanho
}
