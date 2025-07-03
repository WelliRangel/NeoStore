# Neostore - Sistema de Gestão de Fornecedores

## Visão Geral

Aplicação web moderna construída em **React 19** puro com **Vite** como bundler, focada em:

- **Arquitetura Atomic Design** para componentização escalável
- **TypeScript** rigoroso com tipagem forte em toda aplicação
- **Tailwind CSS** com sistema de design customizado
- **Validação robusta** com algoritmos específicos (CNPJ)
- **Interface responsiva** mobile-first com acessibilidade
- **Performance otimizada** com code-splitting e tree-shaking
- **Experiência do usuário** fluida com estados de loading e feedback visual


---

## Estrutura de Pastas

```plaintext
frontend/
├── src/                                    # 📁 Código fonte principal da aplicação
│   ├── components/                         # 📁 Componentes React organizados por Atomic Design
│   │   ├── atoms/                          # ⚛️ Componentes básicos e reutilizáveis
│   │   │   ├── Button.tsx                  # 🔘 Botão com variantes (primary/outline/ghost) e loading
│   │   │   ├── Card.tsx                    # 📄 Container básico para conteúdo com sombra e borda
│   │   │   ├── LoadingSpinner.tsx          # ⏳ Indicador de carregamento com animação CSS
│   │   │   └── Toast.tsx                   # 🔔 Notificação temporária com auto-dismiss e tipos
│   │   ├── molecules/                      # 🧬 Combinações de atoms com lógica específica
│   │   │   ├── FormField.tsx               # 📝 Campo de formulário com validação visual e ARIA
│   │   │   ├── Modal.tsx                   # 🪟 Modal overlay responsivo com backdrop clicável
│   │   │   └── Pagination.tsx              # 📄 Controles de navegação com botões anterior/próximo
│   │   ├── organisms/                      # 🦠 Componentes complexos com múltiplas responsabilidades
│   │   │   ├── ImportModal.tsx             # 📤 Modal para importação JSON com validação de arquivo
│   │   │   ├── SupplierForm.tsx            # 📋 Formulário CRUD com validação real-time
│   │   │   └── SupplierList.tsx            # 📊 Lista responsiva (tabela/cards) com ações
│   │   ├── pages/                          # 📄 Páginas completas que orquestram organisms
│   │   │   └── SuppliersPage.tsx           # 🏢 Página principal de gestão de fornecedores
│   │   └── templates/                      # 📐 Layouts estruturais da aplicação
│   │       └── Layout.tsx                  # 🏗️ Layout principal com header/main/footer fixo
│   ├── services/                           # 🌐 Camada de serviços para comunicação externa
│   │   └── supplierService.ts              # 🔌 Service REST com tratamento centralizado de erros
│   ├── types/                              # 🏷️ Definições TypeScript para contratos de dados
│   │   └── supplier.ts                     # 📋 Interfaces do domínio (Supplier, ApiResponse, etc.)
│   ├── utils/                              # 🛠️ Utilitários e funções auxiliares puras
│   │   ├── formatters.ts                   # 🎭 Formatação de dados com máscaras de CNPJ
│   │   └── validation.ts                   # ✅ Validações com algoritmo completo de CNPJ
│   ├── App.tsx                             # 📱 Componente raiz com gerenciamento de estado global
│   ├── index.css                           # 🎨 Estilos globais Tailwind + CSS customizado
│   └── main.tsx                            # 🚀 Entry point React com createRoot e StrictMode
├── index.html                              # 🌐 HTML base da SPA com meta tags e SEO
├── vite.config.ts                          # ⚙️ Configuração Vite (bundler, HMR, path aliases)
├── package.json                            # 📦 Dependências React 19 + Vite 6 + TypeScript 5.7
├── tsconfig.json                           # 🎯 TypeScript strict mode com path mapping
├── tsconfig.node.json                      # 🔧 Configuração TypeScript para build tools
├── tailwind.config.js                     # 🎨 Sistema de design com cores e animações customizadas
├── postcss.config.js                      # 🔄 Pipeline CSS com Tailwind e Autoprefixer
├── .eslintrc.json                          # ✅ Regras de linting TypeScript + React + Hooks
├── .gitignore                              # 🚫 Exclusões para controle de versão
└── README.md                               # 📖 Documentação completa do projeto
```

---

## Stack Tecnológica

### Core Framework

- **React 19**: Biblioteca JavaScript com hooks modernos e otimizações
- **Vite 6**: Build tool ultra-rápido com HMR e ESM nativo
- **TypeScript 5.7**: Superset tipado com strict mode habilitado


### Styling e UI

- **Tailwind CSS 3.4**: Framework CSS utility-first com tema customizado
- **Lucide React**: Biblioteca de ícones SVG otimizada e consistente
- **CSS Custom Properties**: Sistema de cores com suporte a dark mode


### Tooling e Qualidade

- **ESLint 9**: Linting com regras TypeScript e React específicas
- **PostCSS**: Processamento CSS com Autoprefixer para compatibilidade
- **Vite Plugins**: React plugin com Fast Refresh para desenvolvimento


---

## Padrões Arquiteturais

### Atomic Design

Estrutura hierárquica de componentes baseada na metodologia de Brad Frost:

```typescriptreact
// Atom - Componente básico
<Button variant="primary" loading={isSubmitting}>
  Salvar Fornecedor
</Button>

// Molecule - Combinação de atoms
<FormField label="Email" required error={errors.email}>
  <input type="email" {...register('email')} />
</FormField>

// Organism - Componente complexo
<SupplierForm 
  supplier={editingSupplier}
  onSubmit={handleSubmit}
  onClose={handleClose}
/>
```

### Service Layer Pattern

Abstração da comunicação com APIs externas:

```typescriptreact
class SupplierService {
  private async handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
    }
    return response.json()
  }

  async getSuppliers(page = 1, pageSize = 5): Promise<ApiResponse<Supplier[]>> {
    const response = await fetch(`${API_BASE_URL}/suppliers/?page=${page}&pageSize=${pageSize}`)
    return this.handleResponse<ApiResponse<Supplier[]>>(response)
  }
}
```

### Centralized State Pattern

Estado gerenciado no componente raiz com props drilling:

```typescriptreact
// App.tsx - Estado centralizado
const [suppliers, setSuppliers] = useState<Supplier[]>([])
const [loading, setLoading] = useState(false)
const [toast, setToast] = useState<ToastState | null>(null)

// Handlers especializados
const handleCreateSupplier = async (supplierData: Omit<Supplier, "id">) => {
  try {
    await supplierService.createSupplier(supplierData)
    showToast("Fornecedor criado com sucesso!", "success")
    loadSuppliers(currentPage)
  } catch (error: any) {
    showToast(error.message || "Erro ao criar fornecedor", "error")
  }
}

// Passado via props para componentes filhos
<SuppliersPage
  suppliers={suppliers}
  loading={loading}
  onCreateSupplier={handleCreateSupplier}
/>
```

---

## Decisões Técnicas

### 1. **React Puro com Vite**

**Decisão**: React standalone com Vite como bundler
**Justificativa**:

- **Performance**: Bundle otimizado e carregamento mais rápido
- **Desenvolvimento**: HMR nativo e build instantâneo
- **Simplicidade**: Menos abstrações e maior controle sobre configurações
- **Deploy**: Arquivos estáticos simples para qualquer servidor


### 2. **Vite como Bundler**

**Decisão**: Vite 6 como ferramenta de build principal
**Benefícios**:

```typescript
// vite.config.ts - Configuração otimizada
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: { "@": path.resolve(__dirname, "./src") }
  },
  build: {
    rollupOptions: {
      output: {
        manualChunks: {
          vendor: ["react", "react-dom"],
          icons: ["lucide-react"]
        }
      }
    }
  }
})
```

### 3. **Validação de CNPJ Nativa**

**Decisão**: Implementação própria do algoritmo de validação
**Implementação**:

```typescript
function isValidCNPJ(cnpj: string): boolean {
  const cleanCNPJ = cnpj.replace(/[^\d]/g, "")
  
  if (cleanCNPJ.length !== 14) return false
  if (/^(\d)\1+$/.test(cleanCNPJ)) return false
  
  // Algoritmo completo dos dígitos verificadores
  let sum = 0, weight = 5
  for (let i = 0; i < 12; i++) {
    sum += parseInt(cleanCNPJ[i]) * weight
    weight = weight === 2 ? 9 : weight - 1
  }
  // ... resto da validação
}
```

### 4. **Design Responsivo Mobile-First**

**Estratégia**: Breakpoints adaptativos com componentes híbridos

```typescriptreact
// Desktop: Tabela completa
<div className="hidden lg:block">
  <table className="w-full min-w-[800px]">
    {/* Colunas completas */}
  </table>
</div>

// Mobile: Cards otimizados
<div className="lg:hidden space-y-4">
  {suppliers.map(supplier => (
    <Card key={supplier.id} className="p-4">
      {/* Layout vertical otimizado */}
    </Card>
  ))}
</div>
```

### 5. **Sistema de Cores Semântico**

**Implementação**: CSS Custom Properties com Tailwind

```css
:root {
  --primary: 221.2 83.2% 53.3%;
  --destructive: 0 84.2% 60.2%;
  --border: 214.3 31.8% 91.4%;
}

.dark {
  --primary: 217.2 91.2% 59.8%;
  --destructive: 0 62.8% 30.6%;
  --border: 217.2 32.6% 17.5%;
}
```

### 6. **Tratamento de Erros Centralizado**

**Pattern**: Service layer com error handling consistente

```typescript
private async handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}))
    throw new Error(errorData.message || `HTTP error! status: ${response.status}`)
  }
  return response.json()
}
```

---

## Funcionalidades Implementadas

### **CRUD Completo**

- Listagem paginada com 5 itens por página
- Formulário de criação/edição com validação em tempo real
- Exclusão com confirmação via dialog nativo
- Estados de loading durante todas as operações


### **Importação JSON**

- Upload de arquivos .json com validação de tipo
- Processamento em lote via endpoint `/import`
- Feedback detalhado de resultado (sucessos + erros)
- Estrutura JSON esperada documentada no modal


### **Interface Responsiva**

- Tabela completa em desktop (≥1024px)
- Cards otimizados em mobile (<1024px)
- Layout flexível com breakpoints Tailwind
- Botões e formulários adaptados para touch


### **Validação de Dados**

- Algoritmo completo de validação CNPJ brasileiro
- Validação de email com regex padrão
- Formatação automática de CNPJ durante digitação
- Feedback visual de erros em tempo real


### **Experiência do Usuário**

- Estados de loading em todas as operações assíncronas
- Toasts informativos com auto-dismiss (5 segundos)
- Confirmações para ações destrutivas
- Indicadores visuais de progresso e erro


---

## Performance e Otimizações

### Bundle Splitting

```typescript
// Vite automaticamente separa:
// - vendor.js: React + React DOM
// - icons.js: Lucide React
// - main.js: Código da aplicação
```

### Tree Shaking

- Importação seletiva de ícones Lucide
- Eliminação de código não utilizado
- CSS purging em produção via Tailwind


### Otimizações de Build

- Minificação automática em produção
- Compressão de assets estáticos
- Source maps para debugging


---

## Acessibilidade (a11y)

### Recursos Implementados

- **ARIA labels** em botões de ação específicos
- **Role="alert"** em mensagens de toast
- **Labels semânticos** em todos os campos de formulário
- **Estados de loading** anunciados via aria-live
- **Estrutura semântica** com headings hierárquicos


### Exemplo de Implementação

```typescriptreact
<button
  aria-label={`Editar fornecedor ${supplier.name}`}
  className="flex items-center gap-1"
>
  <Edit className="w-4 h-4" />
  Editar
</button>

<div role="alert" aria-live="polite">
  {toast && <Toast message={toast.message} type={toast.type} />}
</div>
```

---

## Scripts Disponíveis

```shellscript
npm run dev        # Servidor de desenvolvimento com HMR (porta 3000)
npm run build      # TypeScript check + build de produção otimizado  
npm run preview    # Preview local do build de produção
npm run lint       # ESLint com regras TypeScript e React
npm run type-check # Verificação de tipos sem emitir arquivos
```

---

## Requisitos do Sistema

- **Node.js**: 18.0.0 ou superior
- **npm**: 8.0.0 ou superior
- **API Backend**: Rodando em `http://localhost:8080/neostore/api/v1`


---

## Instalação e Execução

### 1. **Clonar o repositório**

```shellscript
git clone <https://github.com/WelliRangel/NeoStore.git>
cd ./NeoStore/frontend
```

### 2. **Instalar dependências**

```shellscript
npm install
```

### 3. **Executar em desenvolvimento**

```shellscript
npm run dev
```

### 4. **Acessar a aplicação**

```plaintext
http://localhost:3000
```

### 5. **Build para produção**

```shellscript
npm run build
npm run preview
```

---

## Estrutura da API

A aplicação consome os seguintes endpoints:

```typescript
// Listar fornecedores (paginado)
GET /suppliers/?page=1&pageSize=5

// Criar fornecedor
POST /suppliers/
Body: { name, email, description, cnpj }

// Atualizar fornecedor
PUT /suppliers/:id
Body: { name, email, description, cnpj }

// Excluir fornecedor
DELETE /suppliers/:id

// Importar em lote
POST /suppliers/import
Body: [{ name, email, description, cnpj }, ...]
```

---

## Próximos Passos

### **Melhorias Imediatas**

- Implementar busca por nome/email
- Adicionar filtros por status ativo/inativo
- Melhorar feedback visual de validação
- Adicionar confirmação antes de importar dados


### **Funcionalidades Futuras**

- Exportação de dados em formato CSV
- Operações em lote com seleção múltipla
- Histórico de alterações por fornecedor
- Cache local com localStorage
- Modo offline básico


### **Qualidade e Testes**

- Testes unitários com Vitest
- Testes de integração com Testing Library
- Testes E2E com Playwright
- Configuração de CI/CD


---

## Contribuição

Este projeto segue as melhores práticas de desenvolvimento React moderno, priorizando **performance**, **acessibilidade** e **experiência do usuário**.

Para contribuições:

1. Siga os padrões estabelecidos de **Atomic Design**
2. Mantenha **TypeScript rigoroso** em todos os arquivos
3. Adicione **testes** para novas funcionalidades
4. Documente **decisões técnicas** significativas


---

## Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---