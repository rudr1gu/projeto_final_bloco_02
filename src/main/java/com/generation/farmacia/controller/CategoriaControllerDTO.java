package com.generation.farmacia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.dto.CategoriaComProdutosDTO;
import com.generation.farmacia.dto.CategoriaDTO;
import com.generation.farmacia.mapper.CategoriaMapper;
import com.generation.farmacia.mapper.ProdutoMapper;
import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * EXEMPLO: CategoriaController usando DTOs
 * 
 * Este é um exemplo de como implementar o controller usando DTOs.
 * Você pode usar este como referência para refatorar seu controller atual.
 */
@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaControllerDTO {
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private CategoriaMapper categoriaMapper;
    
    @Autowired
    private ProdutoMapper produtoMapper;

    

    /**
     * Lista todas as categorias (sem produtos)
     */
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAll() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaDTO> categoriasDTO = categoriaMapper.toDTOList(categorias);
        return ResponseEntity.ok(categoriasDTO);
    }

    /**
     * Busca categoria por ID (sem produtos)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(categoria -> ResponseEntity.ok(categoriaMapper.toDTO(categoria)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Busca categoria por ID com produtos
     */
    @GetMapping("/{id}/completa")
    public ResponseEntity<CategoriaComProdutosDTO> getByIdCompleta(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(categoria -> ResponseEntity.ok(categoriaMapper.toComProdutosDTO(categoria, produtoMapper)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca categorias por nome
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<CategoriaDTO>> getByName(@PathVariable String nome) {
        List<Categoria> categorias = categoriaService.findByName(nome);
        List<CategoriaDTO> categoriasDTO = categoriaMapper.toDTOList(categorias);
        return ResponseEntity.ok(categoriasDTO);
    }

    /**
     * Cria nova categoria
     */
    @PostMapping
    public ResponseEntity<CategoriaDTO> post(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria categoriaSalva = categoriaService.save(categoria);
        CategoriaDTO categoriaRetorno = categoriaMapper.toDTO(categoriaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRetorno);
    }

    /**
     * Atualiza categoria existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> put(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.findById(id)
                .map(categoriaExistente -> {
                    categoriaDTO.setId(id); // Garantir que o ID está correto
                    Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
                    Categoria categoriaAtualizada = categoriaService.save(categoria);
                    return ResponseEntity.ok(categoriaMapper.toDTO(categoriaAtualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deleta categoria
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(categoria -> {
                    categoriaService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
