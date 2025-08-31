# 📋 Guia de Implementação da Camada DTO

## 🎯 O que são DTOs?

**Data Transfer Objects (DTOs)** são objetos que carregam dados entre processos. No seu projeto, eles servem para:

- ✅ **Separar** a API das entidades do banco de dados
- ✅ **Controlar** quais dados são expostos externamente  
- ✅ **Validar** dados de entrada de forma específica
- ✅ **Evitar** vazamento de informações sensíveis
- ✅ **Facilitar** evoluções da API sem impactar o banco

## 🏗️ Estrutura Criada

```
src/main/java/com/generation/farmacia/
├── dto/
│   ├── CategoriaDTO.java              # DTO básico para categoria
│   ├── CategoriaComProdutosDTO.java   # DTO com relacionamentos
│   └── ProdutoDTO.java                # DTO para produto
├── mapper/
│   ├── CategoriaMapper.java           # Converte entre Entity ↔ DTO
│   └── ProdutoMapper.java             # Converte entre Entity ↔ DTO
└── controller/
    ├── CategoriaControllerDTO.java    # Exemplo usando DTOs
    └── ProdutoControllerDTO.java      # Exemplo usando DTOs
```

## 📊 DTOs Criados

### 1. CategoriaDTO
```json
{
  "id": 1,
  "nome": "Antibióticos"
}
```

**Validações:**
- Nome: obrigatório, 5-100 caracteres

### 2. ProdutoDTO
```json
{
  "id": 1,
  "nome": "Amoxicilina 500mg",
  "preco": 25.90,
  "quantidade": 50,
  "validade": "2025-06-30 00:00:00",
  "categoriaId": 1,
  "categoriaNome": "Antibióticos"
}
```

**Validações:**
- Nome: obrigatório, 3-100 caracteres
- Preço: obrigatório, maior que 0
- Quantidade: obrigatória, não negativa
- Validade: obrigatória, data futura
- CategoriaId: obrigatório

### 3. CategoriaComProdutosDTO
```json
{
  "id": 1,
  "nome": "Antibióticos",
  "produtos": [
    {
      "id": 1,
      "nome": "Amoxicilina 500mg",
      "preco": 25.90,
      "quantidade": 50,
      "validade": "2025-06-30 00:00:00",
      "categoriaId": 1,
      "categoriaNome": "Antibióticos"
    }
  ]
}
```

## 🔧 Como Usar

### 1. No Controller

```java
@PostMapping
public ResponseEntity<ProdutoDTO> criarProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
    // 1. Validar categoria existe
    return categoriaService.findById(produtoDTO.getCategoriaId())
        .map(categoria -> {
            // 2. Converter DTO para Entity
            Produto produto = produtoMapper.toEntity(produtoDTO);
            produto.setCategoria(categoria);
            
            // 3. Salvar no banco
            Produto produtoSalvo = produtoService.save(produto);
            
            // 4. Converter Entity para DTO e retornar
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoMapper.toDTO(produtoSalvo));
        })
        .orElse(ResponseEntity.badRequest().build());
}
```

### 2. No Service (opcional - pode manter como está)

```java
@Service
public class ProdutoService {
    
    // Pode manter os métodos atuais que trabalham com Entity
    // Os Controllers fazem a conversão DTO ↔ Entity
    
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }
    
    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }
    
    // ... outros métodos
}
```

## 🚀 Benefícios Implementados

### ✅ **Validações Melhoradas**
- Validade deve ser data futura
- Preço deve ser positivo  
- Quantidade não pode ser negativa
- Mensagens de erro customizadas

### ✅ **Controle de Dados**
- Categoria retorna apenas ID e nome
- Produto retorna categoriaId + categoriaNome (sem lista de produtos)
- Evita referências circulares

### ✅ **Flexibilidade**
- `CategoriaDTO` para operações simples
- `CategoriaComProdutosDTO` para visão completa
- Pode evoluir DTOs independente do banco

### ✅ **Segurança**
- Não expõe anotações JPA (@Entity, @Table, etc.)
- Controla exatamente quais campos são expostos
- Evita ataques de mass assignment

## 📝 Próximos Passos

### 1. **Implementar nos Controllers Atuais**
Substitua seus controllers atuais pelos exemplos criados:
- `CategoriaControllerDTO.java` → `CategoriaController.java`
- `ProdutoControllerDTO.java` → `ProdutoController.java`

### 2. **Adicionar Exception Handling**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        // Tratar erros de validação de DTO
    }
}
```

### 3. **Testes**
Crie testes para validar:
- Conversões Entity ↔ DTO
- Validações dos DTOs
- Endpoints com DTOs

## 🎯 Exemplo de Uso na API

### Criar Produto
```bash
POST /api/produtos
Content-Type: application/json

{
  "nome": "Dipirona 500mg",
  "preco": 12.50,
  "quantidade": 100,
  "validade": "2025-12-31 00:00:00",
  "categoriaId": 1
}
```

### Resposta
```json
{
  "id": 2,
  "nome": "Dipirona 500mg", 
  "preco": 12.50,
  "quantidade": 100,
  "validade": "2025-12-31 00:00:00",
  "categoriaId": 1,
  "categoriaNome": "Analgésicos"
}
```

## ⚡ Dicas Importantes

1. **Sempre validar** categoria existe antes de criar/atualizar produto
2. **Usar @PathVariable** para IDs na URL (não @RequestParam)
3. **Converter Entity → DTO** antes de retornar na API
4. **Usar mensagens de erro** específicas nas validações
5. **Testar** todas as validações implementadas

---

🚀 **Sua API agora está mais robusta, segura e profissional!**
