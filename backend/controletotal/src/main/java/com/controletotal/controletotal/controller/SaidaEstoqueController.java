package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.pdf.PdfDto;
import com.controletotal.controletotal.entity.SaidaItem;
import com.controletotal.controletotal.service.SaidaItemService;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
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
    @Operation(summary = "Gerar relatório de saídas em PDF")
    ResponseEntity<InputStreamResource> gerarRelatorioDeSaidas(
            @RequestParam(required = false)
            @NotNull(message = "Status da solicitação é obrigatório")
            @Schema(allowableValues = {"1", "2"})
            @Min(value = 1, message = "Situação deve ser 1 ou 2")
            @Max(value = 2, message = "Situação deve ser 1 ou 2")
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
        PdfDto pdf = saidaItemService.gerarRelatorioPDF(situacaoSaida, dataInicial, dataFinal);
        String situacao = situacaoSaida == 1 ? "aprovadas" : "reprovadas";

        return prepareResponseDataWithHeaders(situacao, pdf);
    }

    private ResponseEntity<InputStreamResource> prepareResponseDataWithHeaders(String situacao, PdfDto pdf) {
        var responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=relatorio_saidas_%s.pdf", situacao));
        responseHeaders.add("Access-Control-Expose-Headers", String.format("%s", HttpHeaders.CONTENT_DISPOSITION));
        responseHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(pdf.getPdfBinaryData().length));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(new InputStreamResource(new ByteArrayInputStream(pdf.getPdfBinaryData())));
    }
}
