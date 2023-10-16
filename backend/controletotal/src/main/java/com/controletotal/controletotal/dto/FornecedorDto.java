package com.controletotal.controletotal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FornecedorDto {

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    private String nome;

    @NotNull(message = "Número de telefone é obrigatório")
    private String numTelefone;
}
