package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.enums.TipoUsuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario cadastraUsuario(@RequestBody @Valid UsuarioDto usuarioDto){

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.getSenha());
        Usuario novoUsuario = new Usuario(usuarioDto.getNome(), usuarioDto.getEmail(), senhaCriptografada, usuarioDto.getTipo());

        return usuarioRepository.save(novoUsuario);
    }

    public Usuario buscaUsuario(Long id, String nome) {
        if (id != null) {
            return usuarioRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));
        }
        if (nome != null) {
            return usuarioRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o nome: " + nome));
        }
        throw new ErroDeNegocio("Usuario não encontrado");
    }

        public Usuario buscaUsuarioPorEmail(String email) {
        if (email != null) {
            return usuarioRepository.findByEmail(email);
        }
        throw new ErroDeNegocio("Usuario não encontrado");
    }

    public List<Usuario> buscaTodosOsUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizaUsuario(Long id, String nome, String email, TipoUsuario tipo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));

        if (nome != null) {
            usuario.setNome(nome);
        }

        if (usuarioRepository.findByEmailIgnoreCase(email) != null) {
            throw new ErroDeNegocio("Já existe um usuário com esse email");
        }

        if (email != null) {
            usuario.setEmail(email);
        }

        if (tipo != null) {
            usuario.setTipo(tipo);
        }

        return usuarioRepository.save(usuario);
        }

        public void deletaUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}