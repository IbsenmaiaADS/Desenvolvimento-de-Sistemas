package com.controletotal.controletotal.repository;

import com.controletotal.controletotal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNomeIgnoreCase(String nome);

    Usuario findByEmail(String email);

    UserDetails findByEmailIgnoreCase(String email);
}