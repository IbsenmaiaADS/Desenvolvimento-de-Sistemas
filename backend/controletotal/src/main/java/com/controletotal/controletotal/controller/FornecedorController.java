package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.*;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Validated
@RequestMapping("fornecedores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "fornecedores", description = "Gerenciar fornecedores")
public class FornecedorController {
    private final FornecedorService fornecedorService;

    @GetMapping
    @Operation(summary = "Busca todos os fornecedores")
    public ResponseEntity<List<Fornecedor>> buscarTodosOsFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.buscaTodosOsFornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca fornecedores por id ou nome")
    public ResponseEntity<Fornecedor> buscarFornecedorPorNomeOuId(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(fornecedorService.buscaFornecedor(id, nome));
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastra fornecedor")
    public ResponseEntity<Fornecedor> cadastrarFornecedor(@Valid @RequestBody FornecedorDto fornecedorDto) {
        return ResponseEntity.ok(fornecedorService.cadastraFornecedor(fornecedorDto));
    }


    @PatchMapping("/atualizar/{id}")
    @Operation(summary = "Edita fornecedores")
    public ResponseEntity<Fornecedor> atualizarFornecedor(
            @PathVariable
            Long id,
            @RequestParam(required = false)
            String nome,
            @RequestParam(required = false)
            String numTelefone) {
        return ResponseEntity.ok(fornecedorService.atualizaFornecedor(id, nome, numTelefone));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta fornecedores")
    @ResponseStatus(HttpStatus.OK)
    public void deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletaFornecedor(id);
    }

    @GetMapping("/itens")
    @Operation(summary = "Listar itens de um fornecedor")
    public ResponseEntity<List<ItensFornecedorDto>> buscarItensFornecedor(
            @RequestParam(required = false)
            @NotNull(message = "É obrigatório informar o id do fornecedor")
            Long id
    ) {
        return ResponseEntity.ok(fornecedorService.buscarItens(id));
    }

}
