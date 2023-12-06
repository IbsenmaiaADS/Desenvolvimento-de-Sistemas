package com.controletotal.controletotal.entity;

import com.controletotal.controletotal.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_usuario", schema = "controletotal")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "Nome do usuario é obrigatório")
    @Column(name = "nm_usuario")
    private String nome;

    @NotBlank(message = "Um e-mail é obrigatório")
    @Column(name = "email_usuario")
    private String email;

    @NotBlank(message = "Senha é obrigatório") 
    @JsonIgnore
    @Column(name = "senha_usuario")
    private String senha;

    @Column(name = "tipo_usuario")
    private TipoUsuario tipo;

    public Usuario(String nome, String email, String senha, TipoUsuario tipo){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(tipo.equals(TipoUsuario.ADMIN)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_ALMOXARIFE"),
                    new SimpleGrantedAuthority("ROLE_USUARIO"));
        } else if(tipo.equals(TipoUsuario.ALMOXARIFE)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ALMOXARIFE"),
                    new SimpleGrantedAuthority("ROLE_USUARIO"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
        }
    }

    @Nullable
    @Override
    @JsonIgnore
    public String getPassword() {
        return senha;
    }

    @Nullable
    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Nullable
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Nullable
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Nullable
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Nullable
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}