package com.controletotal.controletotal.dto;

public class AutenticacaoDto {
    private String email;
    private String senha;

    public AutenticacaoDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
      }
    
      public AutenticacaoDto() {
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

}
