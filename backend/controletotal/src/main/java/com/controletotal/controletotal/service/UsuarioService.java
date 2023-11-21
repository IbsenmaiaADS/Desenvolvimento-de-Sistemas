package com.controletotal.controletotal.service;

import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.enums.TipoUsuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public Usuario buscaUsuario(Long id, String nome) {
        if (id != null) {
            return usuarioRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));
        }
        if (nome != null) {
            return usuarioRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o nome: " + nome));
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