package com.controletotal.controletotal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.ToString;

@ToString
public class FornecedorDto {

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    private String nome;

    @NotBlank(message = "Número de telefone é obrigatório")
    private String numTelefone;

    public FornecedorDto(String nome, String numTelefone) {
    this.nome = nome;
    this.numTelefone = numTelefone;
    }
    
    public FornecedorDto() {
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumTelefone() {
        return numTelefone;
    }

    public void setNumTelefone(String numTelefone) {
        this.numTelefone = numTelefone;
    }

}
