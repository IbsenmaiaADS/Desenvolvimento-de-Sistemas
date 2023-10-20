package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.entity.SaidaItem;
import com.controletotal.controletotal.service.SaidaItemService;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
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

    @GetMapping("/gerar-relatorio-pdf")
    @Operation(summary = "Gerar um relatório em PDF")
    public ResponseEntity<byte[]> gerarRelatorioDeSaidas(
            @RequestParam(required = false)
            @NotNull(message = "Status da solicitação é obrigatório")
            @Schema(allowableValues = {"1", "2"})
            @Min(value = 1)
            @Max(value = 2)
            Integer situacaoSaida,

            @RequestParam(required = false)
            @NotNull(message = "Data inicial é obrigatório")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate dataInicial,

            @RequestParam(required = false)
            @NotNull(message = "Data final é obrigatório")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate dataFinal
    ) throws DocumentException {
        //ajustar mensagem de erro
        ByteArrayOutputStream pdfStream = saidaItemService.gerarRelatorioPDF(situacaoSaida, dataInicial, dataFinal);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("relatorio.pdf", "relatorio.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }
}
