package com.controletotal.controletotal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "controle-total", description = "API para manter controle de estoque")
public class EstoqueController {

    @GetMapping("teste")
    @Operation(summary = "Valida se a API foi inicializada com sucesso.")
    public String teste() {
        return "Estou funcionando!";
    }
}
