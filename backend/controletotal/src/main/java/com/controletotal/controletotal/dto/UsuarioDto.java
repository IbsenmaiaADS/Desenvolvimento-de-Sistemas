
package com.controletotal.controletotal.dto;

import com.controletotal.controletotal.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
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

}