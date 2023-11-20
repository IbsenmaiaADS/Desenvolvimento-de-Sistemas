package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public Usuario buscaUsuario(Long id, String nome) {
        if (id != null) {
            return usuarioRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));
        } else if (nome != null) {
            return usuarioRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o nome: " + nome));
        }
        throw new ErroDeNegocio("Usuario não encontrado");
    }

//    public Usuario cadastraUsuario(UsuarioDto usuarioDto) {
//        if (usuarioRepository.findByEmailIgnoreCase(usuarioDto.getNome()) != null) {
//            throw new ErroDeNegocio("Já existe um usuario com este nome");
//        }
//
//        Usuario usuario = new Usuario();
//        usuario.setNome(usuarioDto.getNome());
//        usuario.setEmail(usuarioDto.getEmail());
//        usuario.setSenha(usuarioDto.getSenha());
//        usuario.setTipo(usuarioDto.getTipo());
//
//        return usuarioRepository.save(usuario);
//    }

    public Usuario atualizaUsuario(Long id, String email, String nome, String senha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));

        if (nome != null) {
            usuario.setNome(nome);
        }

        if (usuario.getEmail().equals(email) && (usuarioRepository.findByEmailIgnoreCase(email) != null)) {
            throw new ErroDeNegocio("Já existe um usuário com o mesmo email");
        }

        if (email != null) {
            usuario.setEmail(email);
        }

        if (senha != null) {
            usuario.setSenha(senha);
        }

        return usuarioRepository.save(usuario);
    }

    public void deletaUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}