---

# Backend API — Jakarta EE 10 + JPA (Hibernate) + CDI

## Visão Geral

API RESTful desecd..nvolvida com Jakarta EE 10, JPA (Hibernate) e CDI, seguindo princípios de:
- **Separação de Camadas**: Resources (Controllers), Services, Repositories e Entities.
- **Injeção de Dependência**: CDI para abstração e desacoplamento.
- **Padrões de Projeto**: Repository Pattern, Service Layer, Dependency Injection, DTOs e Validação Bean Validation.

---

## Estrutura de Pastas

```
src/
└── main/
    ├── java/
    │   └── com/
    │       └── neostore/
    │           └── suppliers/
    │               ├── api/         # Recursos REST (controllers)
    │               ├── config/      # Configuração da aplicação
    │               ├── dto/         # Data Transfer Objects (records)
    │               ├── exception/   # Exceções customizadas
    │               ├── model/       # Entidades JPA
    │               ├── repository/  # Interfaces de repositório
    │               ├── service/     # Interfaces de serviço
    │               └── validation/  # Validações customizadas
    └── resources/
        └── META-INF/
            └── persistence.xml      # Configuração JPA
```

---

## Principais Padrões e Boas Práticas

- **Repository Pattern**  
  Cada entidade possui um repositório que implementa uma interface, desacoplando regras de persistência.
- **Service Layer**  
  A lógica de negócio reside em Services, que recebem Repositories via CDI.
- **Dependency Injection (CDI)**  
  Injeção de dependências com `@Inject` para promover baixo acoplamento.
- **DTOs e Bean Validation**  
  Uso de records para transporte de dados e validação automática com anotações padrão e customizadas.
- **Validação Customizada**  
  Exemplo: anotação `@Cnpj` para validação de CNPJ.
- **Paginação e Busca**  
  Endpoints REST com suporte a paginação e filtros.
- **Tratamento de Exceções**  
  Exceções customizadas para erros de negócio e validação.

---

## Variáveis de Ambiente e Configuração

- Configure o banco de dados em `src/main/resources/META-INF/persistence.xml`.
- Java 21 é requerido.
- O Maven Wrapper já está incluso.

---

## Scripts de Execução

```bash
# Build e execute localmente (WildFly)
./mvnw clean package wildfly:run

# Build da imagem Docker
./mvnw clean package
docker build -t neostore:v1 .

# Execute via Docker
docker run -it --rm -p 8080:8080 neostore:v1
```

Acesse: [http://localhost:8080/neostore](http://localhost:8080/neostore)

---

## Endpoints de Fornecedor

| Método | Rota                        | Resource (Controller)         | Descrição                        |
|--------|-----------------------------|-------------------------------|----------------------------------|
| GET    | `/api/v1/suppliers`         | SupplierResource#getAll       | Listar fornecedores (paginado)   |
| GET    | `/api/v1/suppliers/{id}`    | SupplierResource#getById      | Detalhar fornecedor              |
| POST   | `/api/v1/suppliers`         | SupplierResource#create       | Criar fornecedor                 |
| PUT    | `/api/v1/suppliers/{id}`    | SupplierResource#update       | Atualizar fornecedor             |
| DELETE | `/api/v1/suppliers/{id}`    | SupplierResource#delete       | Remover fornecedor               |

---

## Exemplos de Requisição (cURL)

- **Listar fornecedores**
  ```bash
  curl -X GET "http://localhost:8080/neostore/api/v1/suppliers?page=1&pageSize=10"
  ```
- **Detalhar fornecedor**
  ```bash
  curl -X GET "http://localhost:8080/neostore/api/v1/suppliers/1"
  ```
- **Criar fornecedor**
  ```bash
  curl -X POST "http://localhost:8080/neostore/api/v1/suppliers" \
       -H "Content-Type: application/json" \
       -d '{"name":"Fornecedor Exemplo","email":"contato@exemplo.com","description":"Fornecedor de produtos diversos","cnpj":"12.345.678/0001-90"}'
  ```
- **Atualizar fornecedor**
  ```bash
  curl -X PUT "http://localhost:8080/neostore/api/v1/suppliers/1" \
       -H "Content-Type: application/json" \
       -d '{"name":"Fornecedor Atualizado","email":"novo@exemplo.com","description":"Nova descrição","cnpj":"12.345.678/0001-90"}'
  ```
- **Remover fornecedor**
  ```bash
  curl -X DELETE "http://localhost:8080/neostore/api/v1/suppliers/1"
  ```

---

## Testes

- Testes automatizados com JUnit 5.
- Execute:  
  ```bash
  ./mvnw test
  ```

---

## Tecnologias Utilizadas

- **Java 21**
- **Jakarta EE 10** (JAX-RS, JPA, CDI, Bean Validation)
- **Hibernate** (implementação JPA)
- **WildFly 35**
- **RESTEasy + Jackson** (serialização JSON)
- **JUnit 5** (testes)
- **Docker** (containerização)
- **Maven Wrapper**

---

## Observações

- O projeto segue arquitetura limpa, com separação clara de responsabilidades.
- Pronto para produção, com suporte a validação, paginação e tratamento de erros.
- Para customizações, consulte os arquivos em `src/main/java/com/neostore/suppliers/`.

---

**Desenvolvido por Wellington Rangel — 2025**
