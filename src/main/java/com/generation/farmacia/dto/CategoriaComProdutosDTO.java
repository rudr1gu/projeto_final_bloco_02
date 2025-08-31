package com.generation.farmacia.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaComProdutosDTO {
    
    private Long id;
    private String nome;
    private List<ProdutoDTO> produtos;
}
