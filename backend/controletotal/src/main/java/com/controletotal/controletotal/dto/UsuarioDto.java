
package com.controletotal.controletotal.dto;

import com.controletotal.controletotal.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Builder
public class UsuarioDto {
    
    @NotBlank(message = "Nome do usuario é obrigatório")
    private String nome;

    @NotBlank(message = "Um e-mail é obrigatório")
    private String email;
    
    @NotBlank(message = "Senha é obrigatório")
    private String senha;

    private TipoUsuario tipo;

    public UsuarioDto(String nome, String email, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
      }
    
    public UsuarioDto() {
    }

    public String getNome() {
    return nome;
    }

    public void setNome(String nome) {
    this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

}