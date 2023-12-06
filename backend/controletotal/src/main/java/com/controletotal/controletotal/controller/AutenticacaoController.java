package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.AutenticacaoDto;
import com.controletotal.controletotal.dto.LoginResponseDTO;
import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "autenticacao", description = "Login e cadastro de usuários")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
        public String loginHome() {
        return "login";
    }

}
