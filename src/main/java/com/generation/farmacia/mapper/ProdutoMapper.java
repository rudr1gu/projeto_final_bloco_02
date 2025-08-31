package com.generation.farmacia.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.generation.farmacia.dto.ProdutoDTO;
import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.model.Produto;

@Component
public class ProdutoMapper {
    
    /**
     * Converte Produto Entity para ProdutoDTO
     */
    public ProdutoDTO toDTO(Produto produto) {
        if (produto == null) {
            return null;
        }
        
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());
        dto.setValidade(produto.getValidade());
        
        if (produto.getCategoria() != null) {
            dto.setCategoriaId(produto.getCategoria().getId());
            dto.setCategoriaNome(produto.getCategoria().getNome());
        }
        
        return dto;
    }
    
    /**
     * Converte ProdutoDTO para Produto Entity
     */
    public Produto toEntity(ProdutoDTO produtoDTO) {
        if (produtoDTO == null) {
            return null;
        }
        
        Produto produto = new Produto();
        produto.setId(produtoDTO.getId());
        produto.setNome(produtoDTO.getNome());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setValidade(produtoDTO.getValidade());
        
        // A categoria será definida no service
        if (produtoDTO.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(produtoDTO.getCategoriaId());
            produto.setCategoria(categoria);
        }
        
        return produto;
    }
    
    /**
     * Converte lista de Produto Entity para lista de ProdutoDTO
     */
    public List<ProdutoDTO> toDTOList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Atualiza uma entidade Produto existente com dados do DTO
     */
    public void updateEntityFromDTO(ProdutoDTO produtoDTO, Produto produto) {
        if (produtoDTO == null || produto == null) {
            return;
        }
        
        produto.setNome(produtoDTO.getNome());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidade(produtoDTO.getQuantidade());
        produto.setValidade(produtoDTO.getValidade());
        // Categoria será atualizada no service
    }
}
