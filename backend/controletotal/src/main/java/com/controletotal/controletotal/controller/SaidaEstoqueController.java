package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.entity.SaidaItem;
import com.controletotal.controletotal.service.SaidaItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("saida-estoque")
@RequiredArgsConstructor
@Tag(name = "saida-estoque", description = "Gerenciar saídas de itens do estoque")
public class SaidaEstoqueController {
    private final SaidaItemService saidaItemService;

    @PostMapping("/solicitar-item")
    @Operation(summary = "Solicita item do estoque")
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

    @GetMapping("/busca-solicitacoes")
    @Operation(summary = "Busca solicitações para saída de estoque")
    public ResponseEntity<List<SaidaItem>> buscarSolicitacoesSaida(
            @RequestParam(required = false)
            @Schema(allowableValues = {"0", "1", "2"})
            Integer situacaoSaida) {
        return ResponseEntity.ok(saidaItemService.buscarSolicitacoes(situacaoSaida));
    }

    @PostMapping("/aprovar-solicitacao/{idSaida}")
    @Operation(summary = "Aprova solicitação para saída de estoque")
    public ResponseEntity<SaidaItem> aprovarSaidaEstoque(@PathVariable Long idSaida) {
        return ResponseEntity.ok(saidaItemService.aprovarSaidaDeEstoque(idSaida));
    }

    @PostMapping("/reprovar-solicitacao/{idSaida}")
    @Operation(summary = "Reprova solicitação para saída de estoque")
    public ResponseEntity<SaidaItem> reprovarSaidaEstoque(@PathVariable Long idSaida) {
        return ResponseEntity.ok(saidaItemService.reprovarSaidaDeEstoque(idSaida));
    }
}
