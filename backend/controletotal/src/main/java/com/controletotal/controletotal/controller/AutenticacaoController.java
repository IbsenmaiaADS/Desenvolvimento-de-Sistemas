package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.AutenticacaoDto;
import com.controletotal.controletotal.dto.LoginResponseDTO;
import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.infra.security.TokenService;
import com.controletotal.controletotal.repository.UsuarioRepository;
import com.controletotal.controletotal.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "autenticacao", description = "Login e cadastro de usuários")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDto autenticacaoDto){
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDto.email(), autenticacaoDto.senha());
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            String token = tokenService.gerarToken((Usuario)
                    auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não existe usuário com essas credenciais.");
        }

    }

    @PostMapping("/cadastrar")
    public Usuario cadastraUsuario(@RequestBody @Valid UsuarioDto usuarioDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastraUsuario(usuarioDto)).getBody();
    }

}
