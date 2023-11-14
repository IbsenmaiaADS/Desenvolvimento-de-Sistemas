package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario buscaUsuario(Long id, String email) {
        if (id != null) {
            return usuarioRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));
        } else if (email != null) {
            return usuarioRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o email: " + email));
        }
        throw new ErroDeNegocio("Usuario não encontrado");
    }

    public Usuario cadastraUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.findByEmailIgnoreCase(usuarioDto.getEmail()).isPresent()) {
            throw new ErroDeNegocio("Já existe um usuario com este e-mail");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());

        String senhaCriptografada = criptografarMD5(usuarioDto.getSenha());
        usuario.setSenha(senhaCriptografada);

        usuario.setTipo(usuarioDto.getTipo());

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizaUsuario(Long id, String email, String nome, String senha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ErroDeNegocio("Usuário não encontrado com o ID: " + id));

        if (nome != null) {
            usuario.setNome(nome);
        }

        if (usuario.getEmail().equals(email) && usuarioRepository.findByEmailIgnoreCase(email).isPresent()) {
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

    private String criptografarMD5(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar a senha", e);
        }
    }