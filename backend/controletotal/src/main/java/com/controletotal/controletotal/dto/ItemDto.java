package com.controletotal.controletotal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ItemDto {
    @NotBlank(message = "Nome do item é obrigatório")
    private String nome;

    @NotNull(message = "Quantidade em estoque é obrigatório")
    @Positive(message = "Quantidade em estoque deve ser positivo")
    private Integer quantidadeEstoque;
}
