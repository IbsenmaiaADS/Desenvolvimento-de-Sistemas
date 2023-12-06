package com.controletotal.controletotal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDto {
    @NotBlank(message = "Nome do item é obrigatório")
    private String nome;

    @NotNull(message = "Fornecedor do item é obrigatório")
    private Long idFornecedor;

    @Positive(message = "Valor do item deve ser positivo")
    @NotNull(message = "Valor do item é obrigatório")
    private Double valor;
}
