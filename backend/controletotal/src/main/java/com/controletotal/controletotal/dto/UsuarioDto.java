
package com.controletotal.controletotal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UsuarioDto {
    
    @NotBlank(message = "Nome do usuario é obrigatório")
    private String nome;

    @NotBlank(message = "Um e-mail é obrigatório")
    private String email;
    
    @NotBlank(message = "Senha é obrigatório")
    private String senha;

    @NotBlank(message = "O tipo do usuário é obrigatório")
    private Integer tipo;

}