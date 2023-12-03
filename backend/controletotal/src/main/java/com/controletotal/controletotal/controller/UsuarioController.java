package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.enums.TipoUsuario;
import com.controletotal.controletotal.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("usuario")
@RequiredArgsConstructor
@Tag(name = "usuario", description = "Gerenciar usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/buscar")
    @Operation(summary = "Buscar um usuario")
    public ResponseEntity<Usuario> buscarUsuario(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome
    ) {
        return ResponseEntity.ok(usuarioService.buscaUsuario(id, nome));
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários")
    public ResponseEntity<List<Usuario>> buscarTodosOsUsuarios() {
        List<Usuario> usuarios = usuarioService.buscaTodosOsUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PatchMapping("/atualizar")
    @Operation(summary = "Edita usuario")
    public ResponseEntity<Usuario> atualizarUsuario(
            @RequestParam(required = false)
            @NotNull(message = "É obrigatório informar o id do usuário")
            Long id,
            @RequestParam(required = false)
            String nome,
            @RequestParam(required = false)
            String email,
            @RequestParam(required = false)
            TipoUsuario tipo
    ) {
        return ResponseEntity.ok(usuarioService.atualizaUsuario(id, nome, email, tipo));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta usuario")
    @ResponseStatus(HttpStatus.OK)
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletaUsuario(id);
    }
}
