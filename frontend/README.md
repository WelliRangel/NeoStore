# Neostore - Sistema de Gestão de Fornecedores (React Puro)

Sistema moderno de gestão de fornecedores desenvolvido com React puro e Vite.

## 🚀 Tecnologias

- **React 19** - Biblioteca JavaScript para interfaces
- **Vite 6** - Build tool e dev server ultra-rápido
- **TypeScript 5.7** - Superset tipado do JavaScript
- **Tailwind CSS 3.4** - Framework CSS utilitário
- **Lucide React** - Biblioteca de ícones moderna

## 📋 Pré-requisitos

- Node.js 18.0.0 ou superior
- npm 8.0.0 ou superior

## 🛠️ Instalação

1. **Clone o repositório:**
\`\`\`bash
git clone <repository-url>
cd neostore-react-pure
\`\`\`

2. **Instale as dependências:**
\`\`\`bash
npm install
\`\`\`

3. **Execute em desenvolvimento:**
\`\`\`bash
npm run dev
\`\`\`

4. **Acesse:** http://localhost:3000

## 📦 Scripts Disponíveis

- `npm run dev` - Executa em modo desenvolvimento
- `npm run build` - Gera build de produção
- `npm run preview` - Preview do build de produção
- `npm run lint` - Executa linting do código
- `npm run type-check` - Verifica tipos TypeScript

## 🏗️ Arquitetura

O projeto segue o padrão **Atomic Design**:

\`\`\`
src/
├── components/
│   ├── atoms/          # Componentes básicos
│   ├── molecules/      # Combinações de atoms
│   ├── organisms/      # Componentes complexos
│   ├── templates/      # Layouts
│   └── pages/          # Páginas completas
├── services/           # Comunicação com API
├── types/              # Definições TypeScript
├── utils/              # Utilitários
└── App.tsx             # Componente principal
\`\`\`

## 🎯 Funcionalidades

- ✅ CRUD completo de fornecedores
- ✅ Validação de formulários com máscaras
- ✅ Importação em lote via JSON
- ✅ Interface totalmente responsiva
- ✅ Paginação com navegação
- ✅ Feedback visual com toasts
- ✅ Estados de loading
- ✅ Tratamento de erros

## 🔧 API

O sistema consome a API REST em:
`http://localhost:8080/neostore/api/v1/suppliers/`

## 📱 Responsividade

- **Mobile (< 1024px)**: Layout em cards otimizado
- **Desktop (≥ 1024px)**: Tabela completa com todas as colunas
- **Transições suaves** entre breakpoints

## 🚀 Deploy

Para fazer build de produção:

\`\`\`bash
npm run build
npm run preview
\`\`\`

Os arquivos serão gerados na pasta `dist/`.

## ⚡ Performance

- **Bundle splitting** automático
- **Tree shaking** para código não utilizado
- **CSS purging** em produção
- **Lazy loading** de componentes
- **Otimização de imagens**

## 🔧 Configuração

### Vite
- Alias `@/` para `src/`
- Hot Module Replacement (HMR)
- Build otimizado com Rollup

### Tailwind CSS
- Configuração customizada
- Plugins para formulários e tipografia
- Sistema de cores consistente

### TypeScript
- Strict mode habilitado
- Path mapping configurado
- Verificação de tipos rigorosa

## 📄 Licença

Este projeto está sob a licença MIT.
