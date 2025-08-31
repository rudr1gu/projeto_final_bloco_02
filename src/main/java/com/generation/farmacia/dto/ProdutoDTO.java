package com.generation.farmacia.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    
    private Long id;
    
    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private Double preco;
    
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantidade;
    
    @NotNull(message = "A data de validade é obrigatória")
    @Future(message = "A data de validade deve ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Brazil/East")
    private Date validade;
    
    @NotNull(message = "A categoria é obrigatória")
    private Long categoriaId;
    
    // Campo para retorno - nome da categoria
    private String categoriaNome;
}
