package com.controletotal.controletotal.entity;

import com.controletotal.controletotal.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
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
@Table(name = "tb_usuario", schema = "controletotal", uniqueConstraints = @UniqueConstraint(columnNames={"id_usuario", "nm_usuario", "email_usuario"}))
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank
    @Column(name = "nm_usuario")
    private String nome;

    @NotBlank
    @Column(name = "email_usuario")
    private String email;

    @NotBlank
    @Column(name = "senha_usuario")
    private String senha;

    @NotBlank
    @Column(name = "tipo_usuario")
    private TipoUsuario tipo;

    public Usuario(String nome, String login, String senha, TipoUsuario tipo){
        this.nome = nome;
        this.email = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(tipo.equals(TipoUsuario.ADMIN)) {
            return List.of(new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("ALMOXARIFE"),
                    new SimpleGrantedAuthority("VISITANTE"));
        } else if(tipo.equals(TipoUsuario.ALMOXARIFE)) {
            return List.of(new SimpleGrantedAuthority("ALMOXARIFE"));
        } else {
            return List.of(new SimpleGrantedAuthority("VISITANTE"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}