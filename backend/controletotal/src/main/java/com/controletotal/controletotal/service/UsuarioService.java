package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.enums.TipoUsuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario cadastraUsuario(@RequestBody @Valid @NotBlank UsuarioDto usuarioDto){
        if(usuarioRepository.findByEmailIgnoreCase(usuarioDto.getEmail()) != null) {
            throw new ErroDeNegocio("Já existe um usuário com este e-mail!");
        }

        if(usuarioDto.getEmail() != null &&
        usuarioDto.getNome() != null &&
        usuarioDto.getSenha() != null &&
        usuarioDto.getTipo() != null) {
            String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.getSenha());
            Usuario novoUsuario = new Usuario(usuarioDto.getNome(), usuarioDto.getEmail(), senhaCriptografada, usuarioDto.getTipo());

            return usuarioRepository.save(novoUsuario);
        }

        throw new ErroDeNegocio("Os campos não podem ser nulos");
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

    public Usuario buscaUsuarioPorId(Long id) {
        if (id != null) {
            return usuarioRepository.findById(id).get();
        }
        throw new ErroDeNegocio("Usuario não encontrado");
    }


    public List<Usuario> buscaTodosOsUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizaUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));

        if (usuarioDto.getNome() != null) {
            usuario.setNome(usuarioDto.getNome());
        }

         if (usuarioDto.getTipo() != null) {
            usuario.setTipo(usuarioDto.getTipo());
        }

        return usuarioRepository.save(usuario);
        }

        public void deletaUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}