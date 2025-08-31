package com.generation.farmacia.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.generation.farmacia.dto.CategoriaComProdutosDTO;
import com.generation.farmacia.dto.CategoriaDTO;
import com.generation.farmacia.model.Categoria;

@Component
public class CategoriaMapper {
    
    /**
     * Converte Categoria Entity para CategoriaDTO
     */
    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        
        return new CategoriaDTO(
            categoria.getId(),
            categoria.getNome()
        );
    }
    
    /**
     * Converte CategoriaDTO para Categoria Entity
     */
    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            return null;
        }
        
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        
        return categoria;
    }
    
    /**
     * Converte lista de Categoria Entity para lista de CategoriaDTO
     */
    public List<CategoriaDTO> toDTOList(List<Categoria> categorias) {
        return categorias.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Converte Categoria Entity para CategoriaComProdutosDTO (com produtos)
     */
    public CategoriaComProdutosDTO toComProdutosDTO(Categoria categoria, ProdutoMapper produtoMapper) {
        if (categoria == null) {
            return null;
        }
        
        CategoriaComProdutosDTO dto = new CategoriaComProdutosDTO();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        
        if (categoria.getProdutos() != null) {
            dto.setProdutos(produtoMapper.toDTOList(categoria.getProdutos()));
        }
        
        return dto;
    }
}
