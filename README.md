---

# Monorepo Fullstack — Jakarta EE & React 19  
[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)]()  
[![License](https://img.shields.io/badge/license-MIT-green.svg)]()  
[![Docker](https://img.shields.io/badge/docker-ready-blue.svg)]()  

## Descrição

Este repositório contém um monorepo fullstack dividido em dois projetos:

- **Backend**: API RESTful desenvolvida em Java 21 com Jakarta EE 10, JPA (Hibernate), CDI e WildFly.
- **Frontend**: Aplicação web moderna em React 19, Vite, TypeScript e Tailwind CSS.

A orquestração do ambiente de desenvolvimento e produção é feita através de containers Docker, com Docker Compose gerenciando os serviços de API, banco de dados (caso exista) e frontend.

---

## 📑 Sumário

1. [Tecnologias Utilizadas](#tecnologias-utilizadas)
2. [Pré-requisitos](#pré-requisitos)
3. [Instalação](#instalação)
4. [Configuração de Ambiente](#configuração-de-ambiente)
5. [Comandos Úteis](#comandos-úteis)
6. [Estrutura do Projeto](#estrutura-do-projeto)
7. [Execução Local](#execução-local)
8. [Testes](#testes)
9. [Contribuição](#contribuição)
10. [Licença](#licença)
11. [Contato](#contato)

---

## Tecnologias Utilizadas

| Camada    | Tecnologia                                       |
|-----------|--------------------------------------------------|
| Backend   | Java 21, Jakarta EE 10, JPA (Hibernate), WildFly |
| Frontend  | React 19, Vite, TypeScript, Tailwind CSS         |
| DevOps    | Docker, Docker Compose                           |

---

## Pré-requisitos

- **Git** (v2.0 ou superior)
- **Docker Engine** (v20.10 ou superior)
- **Docker Compose** (v1.29 ou superior)

---

## Instalação

. Clone este repositório:
   ```bash
   git clone https://github.com/WelliRangel/NeoStore.git
   cd NeoStore
   ```

. (Opcional) Configure variáveis de ambiente do backend e frontend conforme instruções nos READMEs de cada pasta, se necessário.

---

## Configuração de Ambiente

O arquivo `docker-compose.yml` está localizado na raiz e define os seguintes serviços:

- **backend**: Container Jakarta EE (WildFly)
- **frontend**: Container React (Vite)
- **db**: Container de banco de dados (caso aplicável)

---

## Comandos Úteis

| Comando                                 | Descrição                                         |
|------------------------------------------|---------------------------------------------------|
| `docker compose up -d --build`           | Builda imagens e inicia todos os serviços         |
| `docker compose down -v`                 | Para e remove containers e volumes                |
| `docker compose logs -f`                 | Visualiza logs dos serviços em tempo real         |

---

## Estrutura do Projeto

```
/
├── backend/    # API Jakarta EE (Java, WildFly)
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── README.md
├── frontend/   # React 19 + Vite + Tailwind
│   ├── src/
│   ├── package.json
│   ├── Dockerfile
│   └── README.md
├── docker-compose.yml
└── README.md   # (este arquivo)
```

---

## Execução Local

Após clonar o repositório e garantir que as variáveis de ambiente estão corretas (se necessário):

```bash
# No diretório raiz do projeto
docker compose up -d --build
```

- O **backend** ficará disponível em: [http://localhost:8080/neostore](http://localhost:8080/neostore)
- O **frontend** ficará disponível em: [http://localhost:3000](http://localhost:3000)

> **Obs:** O Docker Compose irá construir e subir todos os serviços automaticamente. Não é necessário rodar comandos individuais em cada pasta.

---

## Testes

Consulte os READMEs em `/backend` e `/frontend` para instruções de testes automatizados de cada projeto.

---

## Contribuição

. Faça um fork deste repositório
. Crie uma branch feature: `git checkout -b feature/nome-da-feature`
. Faça commit das suas alterações: `git commit -m 'Adiciona nova feature'`
. Envie para o repositório remoto: `git push origin feature/nome-da-feature`
. Abra um Pull Request descrevendo suas alterações

---

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

---

## Contato

- **Autor**: Wellington Rangel
- **E-mail**: wellirangel.dev@gmail.com
- **LinkedIn**: https://www.linkedin.com/in/wellington-rangel/

---

> Consulte os READMEs em `/backend` e `/frontend` para detalhes completos de cada projeto.

---




