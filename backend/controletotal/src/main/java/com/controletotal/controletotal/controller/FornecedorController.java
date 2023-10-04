package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.*;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("fornecedores")
@RequiredArgsConstructor
@Tag(name = "controle-total", description = "Gerenciar fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @GetMapping("teste")
    @Operation(summary = "Valida se a API foi inicializada com sucesso.")
    public String teste() {
        return "Estou funcionando!";
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> buscarTodosOsFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.buscaTodosOsFornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Fornecedor> buscarFornecedorPorNomeOuId(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(fornecedorService.buscaFornecedor(id, nome));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Fornecedor> cadastrarFornecedor(@Valid @RequestBody FornecedorDto fornecedorDto) {
        return ResponseEntity.ok(fornecedorService.cadastraFornecedor(fornecedorDto));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Long id, @RequestBody FornecedorDto fornecedorDto) {
        return ResponseEntity.ok(fornecedorService.atualizaFornecedor(id, fornecedorDto));
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletaFornecedor(id);
    }
}
