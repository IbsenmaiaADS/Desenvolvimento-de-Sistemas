package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.AutenticacaoDto;
import com.controletotal.controletotal.dto.LoginResponseDTO;
//import com.controletotal.controletotal.infra.security.TokenService;
import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.infra.security.TokenService;
import com.controletotal.controletotal.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Usuario)
                auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastraUsuario(@RequestBody @Valid UsuarioDto usuarioDto){
        if(usuarioRepository.findByEmailIgnoreCase(usuarioDto.getEmail()) != null) {
            throw new ErroDeNegocio("Já existe um usuario com este nome");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.getSenha());
        Usuario novoUsuario = new Usuario(usuarioDto.getNome(), usuarioDto.getEmail(), senhaCriptografada, usuarioDto.getTipo());

        usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();

    }

}
