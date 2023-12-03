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
    // @Autowired
    // private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
        public String loginHome() {
        return "login";
    }

    @GetMapping("/cadastrar")
    public String showRegistrationForm(Model model){
        UsuarioDto usuario = new UsuarioDto();
        model.addAttribute("usuario", usuario);
        return "cadastrar";
    }

    // @PostMapping("/login")
    // public ResponseEntity login(@RequestBody @Valid AutenticacaoDto autenticacaoDto){
    //     try {
    //         UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(autenticacaoDto.getEmail(), autenticacaoDto.getSenha());
    //         Authentication auth = authenticationManager.authenticate(usernamePassword);

    //         String token = tokenService.gerarToken((Usuario)
    //                 auth.getPrincipal());

    //         return ResponseEntity.ok(new LoginResponseDTO(token));
    //     } catch (AuthenticationException ex) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não existe usuário com essas credenciais.");
    //     }

    // }

    @PostMapping("/cadastrar/save")
    public String cadastraUsuario(@RequestBody @ModelAttribute("usuario") @Valid UsuarioDto usuarioDto, BindingResult result, Model model){

        if (usuarioService.buscaUsuarioPorEmail(usuarioDto.getEmail()) != null) {
            result.rejectValue("email", null, "Já existe um usuário registrado com esse email.");
        } 

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuarioDto);
            return "cadastrar";
        }

        ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastraUsuario(usuarioDto)).getBody();
        return "redirect:/cadastrar?success";
    }

}
