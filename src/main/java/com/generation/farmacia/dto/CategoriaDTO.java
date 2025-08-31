package com.generation.farmacia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;
    
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(min = 5, max = 100, message = "O nome deve ter entre 5 e 100 caracteres")
    private String nome;
}
