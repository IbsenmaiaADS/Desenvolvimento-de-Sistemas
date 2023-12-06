package com.controletotal.controletotal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "autenticacao", description = "Login de usuários")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
        public String loginHome() {
        return "login";
    }

}
