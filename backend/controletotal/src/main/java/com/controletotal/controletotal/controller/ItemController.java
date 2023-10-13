package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.ItemDto;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.SaidaItem;
import com.controletotal.controletotal.service.ItemService;
import com.controletotal.controletotal.service.SaidaItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("itens")
@RequiredArgsConstructor
@Tag(name = "controle-total", description = "Gerenciar itens do estoque")
public class ItemController {

    private final ItemService itemService;
    private final SaidaItemService saidaItemService;

    @GetMapping
    @Operation(summary = "Busca todos os itens")
    public ResponseEntity<List<Item>> buscarTodosOsItens() {
        List<Item> itens = itemService.buscaTodosOsItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca itens por id ou nome")
    public ResponseEntity<Item> buscarItemPorNomeOuId(@RequestParam(required = false) Long id,
                                                      @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(itemService.buscaItem(id, nome));
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastra itens")
    public ResponseEntity<Item> cadastrarItem(@Valid @RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.cadastraItem(itemDto));
    }

    @PatchMapping("/atualizar")
    @Operation(summary = "Edita itens")
    public ResponseEntity<Item> atualizarItem(
            @RequestParam(required = false)
            @NotNull(message = "Id do item é obrigatório")
            Long id,

            @RequestParam(required = false)
            String nome,

            @RequestParam(required = false)
            @Positive(message = "Quantidade em estoque deve ser positivo")
            Integer quantidadeEstoque) {
        return ResponseEntity.ok(itemService.atualizaItem(id, quantidadeEstoque, nome));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta itens")
    @ResponseStatus(HttpStatus.OK)
    public void deletarItem(@PathVariable Long id) {
        itemService.deletaItem(id);
    }

    @PostMapping("/solicitar-item")
    public ResponseEntity<SaidaItem> solicitarItem(
            @RequestParam(required = false)
            @NotNull(message = "Id do item é obrigatório")
            Long idItem,

            @RequestParam(required = false)
            @NotNull(message = "Quantidade do item é obrigatório")
            @Positive(message = "Quantidade em estoque deve ser positivo")
            @DefaultValue(value = "1")
            Integer quantidade
    ) {
        return ResponseEntity.ok(saidaItemService.solicitarItem(idItem, quantidade));
    }

    @GetMapping("/aguardando-aprovacao")
    public ResponseEntity<List<SaidaItem>> buscarItensAguardandoAprovacao() {
        return ResponseEntity.ok(saidaItemService.buscarItensAguardandoAprovacao());
    }
}
