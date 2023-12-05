package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.CompraDto;
import com.controletotal.controletotal.dto.pdf.PdfDto;
import com.controletotal.controletotal.entity.Compra;
import com.controletotal.controletotal.service.CompraService;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@RestController
@Validated
@RequestMapping("compras")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "compras", description = "Gerenciar compras")
public class ComprasController {
    private final CompraService compraService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar nova compra")
    public ResponseEntity<Compra> cadastrarFornecedor(@Valid @RequestBody CompraDto compraDto) {
        return ResponseEntity.ok(compraService.cadastrarCompra(compraDto));
    }

    @GetMapping("/gerar-relatorio-pdf")
    @Operation(summary = "Gerar relatório de compras em PDF")
    ResponseEntity<InputStreamResource> gerarRelatorioDeCompras(
            @RequestParam(required = false)
            @NotNull(message = "Data inicial é obrigatório")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate dataInicial,

            @RequestParam(required = false)
            @NotNull(message = "Data final é obrigatório")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate dataFinal,

            @RequestParam(required = false)
            Long fornecedor

    ) throws DocumentException {
        PdfDto pdf = compraService.gerarRelatorioPDF(dataInicial, dataFinal, fornecedor);
        String situacao = fornecedor == null ? "compras" : "compras_por_fornecedor";

        return prepareResponseDataWithHeaders(situacao, pdf);
    }

    private ResponseEntity<InputStreamResource> prepareResponseDataWithHeaders(String situacao, PdfDto pdf) {
        var responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=relatorio_%s.pdf", situacao));
        responseHeaders.add("Access-Control-Expose-Headers", String.format("%s", HttpHeaders.CONTENT_DISPOSITION));
        responseHeaders.add("Access-Control-Expose-Headers", String.format("%s", HttpHeaders.CONTENT_DISPOSITION));
        responseHeaders.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(pdf.getPdfBinaryData().length));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(new InputStreamResource(new ByteArrayInputStream(pdf.getPdfBinaryData())));
    }
}
