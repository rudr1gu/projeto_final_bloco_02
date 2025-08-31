# 💊 Sistema de Farmácia - API REST

Uma API REST completa para gerenciamento de farmácia desenvolvida com Spring Boot, permitindo o controle de produtos farmacêuticos e suas categorias.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.0**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **MySQL**
- **Lombok**
- **Maven**

## 📋 Funcionalidades

### 🏷️ Categorias
- ✅ Listar todas as categorias
- ✅ Buscar categoria por ID
- ✅ Buscar categorias por nome
- ✅ Criar nova categoria
- ✅ Atualizar categoria existente
- ✅ Deletar categoria

### 💊 Produtos
- ✅ Listar todos os produtos
- ✅ Buscar produto por ID
- ✅ Buscar produtos por nome
- ✅ Criar novo produto
- ✅ Atualizar produto existente
- ✅ Deletar produto
- ✅ Controle de validade de produtos

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/generation/farmacia/
│   │       ├── FarmaciaApplication.java
│   │       ├── controller/
│   │       │   ├── CategoriaController.java
│   │       │   └── ProdutoController.java
│   │       ├── model/
│   │       │   ├── Categoria.java
│   │       │   └── Produto.java
│   │       └── repository/
│   │           ├── CategoriaRepository.java
│   │           └── ProdutoRepository.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/generation/farmacia/
            └── FarmaciaApplicationTests.java
```

## 📊 Modelo de Dados

### Categoria
```json
{
  "id": 1,
  "nome": "Analgésicos",
  "produtos": []
}
```

### Produto
```json
{
  "id": 1,
  "nome": "Paracetamol 500mg",
  "preco": 15.99,
  "quantidade": 100,
  "validade": "2025-12-31 00:00:00",
  "categoria": {
    "id": 1,
    "nome": "Analgésicos"
  }
}
```

## 🔧 Configuração e Instalação

### Pré-requisitos
- Java 17 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior

### 1. Clone o repositório
```bash
git clone https://github.com/rudr1gu/projeto_final_bloco_02.git
cd projeto_final_bloco_02
```

### 2. Configure o banco de dados
1. Certifique-se de que o MySQL está rodando
2. O banco `db_farmacia` será criado automaticamente
3. Configure as credenciais no `application.properties` se necessário:

```properties
spring.datasource.url=jdbc:mysql://localhost/db_farmacia?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSl=false
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 3. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Endpoints da API

### Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/categorias` | Lista todas as categorias |
| GET | `/categorias/{id}` | Busca categoria por ID |
| GET | `/categorias/nome/{nome}` | Busca categorias por nome |
| POST | `/categorias` | Cria nova categoria |
| PUT | `/categorias` | Atualiza categoria |
| DELETE | `/categorias/{id}` | Deleta categoria |

### Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos` | Lista todos os produtos |
| GET | `/produtos/{id}` | Busca produto por ID |
| GET | `/produtos/nome/{nome}` | Busca produtos por nome |
| POST | `/produtos` | Cria novo produto |
| PUT | `/produtos` | Atualiza produto |
| DELETE | `/produtos/{id}` | Deleta produto |

## 🧪 Exemplos de Uso

### Criar uma categoria
```bash
curl -X POST http://localhost:8080/categorias \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Antibióticos"
  }'
```

### Criar um produto
```bash
curl -X POST http://localhost:8080/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Amoxicilina 500mg",
    "preco": 25.90,
    "quantidade": 50,
    "validade": "2025-06-30 00:00:00",
    "categoria": {
      "id": 1
    }
  }'
```

### Buscar produtos por nome
```bash
curl http://localhost:8080/produtos/nome/amoxicilina
```

## ✅ Validações

### Categoria
- Nome: obrigatório, entre 5 e 100 caracteres

### Produto
- Nome: obrigatório, entre 3 e 100 caracteres
- Preço: obrigatório, valor numérico
- Quantidade: obrigatório, valor inteiro
- Validade: obrigatório, formato de data
- Categoria: relacionamento obrigatório

## 🌐 CORS

A API está configurada para aceitar requisições de qualquer origem (`origins = "*"`), facilitando o desenvolvimento e integração com frontends.

## 🛠️ Desenvolvimento

### Compilar o projeto
```bash
mvn clean compile
```

### Executar testes
```bash
mvn test
```

### Gerar JAR
```bash
mvn clean package
```

## 👨‍💻 Autor

**Rodrigo** - [rudr1gu](https://github.com/rudr1gu)

## 📄 Licença

Este projeto foi desenvolvido como projeto final do Bloco 02 - Generation Brasil.

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!