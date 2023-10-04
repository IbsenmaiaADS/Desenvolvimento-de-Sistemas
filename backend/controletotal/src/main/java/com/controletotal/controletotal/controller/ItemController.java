package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.*;
import com.controletotal.controletotal.entity.Item;
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
@RequestMapping("itens")
@RequiredArgsConstructor
@Tag(name = "controle-total", description = "Gerenciar itens do estoque")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("teste")
    @Operation(summary = "Valida se a API foi inicializada com sucesso.")
    public String teste() {
        return "Estou funcionando!";
    }

    @GetMapping
    public ResponseEntity<List<Item>> buscarTodosOsItens() {
        List<Item> itens = itemService.buscaTodosOsItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Item> buscarItemPorNomeOuId(@RequestParam(required = false) Long id,
                                                      @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(itemService.buscaItem(id, nome));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Item> cadastrarItem(@Valid @RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.cadastraItem(itemDto));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Item> atualizarItem(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.atualizaItem(id, itemDto));
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletarItem(@PathVariable Long id) {
        itemService.deletaItem(id);
    }
}
