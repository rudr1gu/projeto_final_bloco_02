package com.generation.farmacia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.dto.ProdutoDTO;
import com.generation.farmacia.mapper.ProdutoMapper;
import com.generation.farmacia.model.Produto;
import com.generation.farmacia.service.CategoriaService;
import com.generation.farmacia.service.ProdutoService;

import jakarta.validation.Valid;

/**
 * EXEMPLO: ProdutoController usando DTOs
 * 
 * Este é um exemplo de como implementar o controller usando DTOs.
 * Você pode usar este como referência para refatorar seu controller atual.
 */
@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoControllerDTO {
    
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ProdutoMapper produtoMapper;

    /**
     * Lista todos os produtos
     */
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAll() {
        List<Produto> produtos = produtoService.findAll();
        List<ProdutoDTO> produtosDTO = produtoMapper.toDTOList(produtos);
        return ResponseEntity.ok(produtosDTO);
    }

    /**
     * Busca produto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> ResponseEntity.ok(produtoMapper.toDTO(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca produtos por nome
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProdutoDTO>> getByName(@PathVariable String nome) {
        List<Produto> produtos = produtoService.findByName(nome);
        List<ProdutoDTO> produtosDTO = produtoMapper.toDTOList(produtos);
        return ResponseEntity.ok(produtosDTO);
    }

    /**
     * Cria novo produto
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> post(@Valid @RequestBody ProdutoDTO produtoDTO) {
        // Validar se a categoria existe
        return categoriaService.findById(produtoDTO.getCategoriaId())
                .map(categoria -> {
                    Produto produto = produtoMapper.toEntity(produtoDTO);
                    produto.setCategoria(categoria); // Definir a categoria completa
                    Produto produtoSalvo = produtoService.save(produto);
                    ProdutoDTO produtoRetorno = produtoMapper.toDTO(produtoSalvo);
                    return ResponseEntity.status(HttpStatus.CREATED).body(produtoRetorno);
                })
                .orElse(ResponseEntity.badRequest().build()); // Categoria não encontrada
    }

    /**
     * Atualiza produto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> put(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        return produtoService.findById(id)
                .map(produtoExistente -> {
                    // Validar se a categoria existe
                    return categoriaService.findById(produtoDTO.getCategoriaId())
                            .map(categoria -> {
                                produtoDTO.setId(id); // Garantir que o ID está correto
                                produtoMapper.updateEntityFromDTO(produtoDTO, produtoExistente);
                                produtoExistente.setCategoria(categoria);
                                Produto produtoAtualizado = produtoService.save(produtoExistente);
                                return ResponseEntity.ok(produtoMapper.toDTO(produtoAtualizado));
                            })
                            .orElse(ResponseEntity.badRequest().<ProdutoDTO>build()); // Categoria não encontrada
                })
                .orElse(ResponseEntity.notFound().build()); // Produto não encontrado
    }

    /**
     * Deleta produto
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> {
                    produtoService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
