package com.controletotal.controletotal.repository;

import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailIgnoreCase(String email);

    UserDetails findByUserEmailIgnoreCase(String email);
}