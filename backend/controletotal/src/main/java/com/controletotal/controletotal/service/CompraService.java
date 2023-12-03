package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.CompraDto;
import com.controletotal.controletotal.dto.pdf.ComprasResponseDto;
import com.controletotal.controletotal.dto.pdf.PdfDto;
import com.controletotal.controletotal.entity.Compra;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.CompraRepository;
import com.controletotal.controletotal.repository.ItemRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService {
    private final CompraRepository compraRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final FornecedorService fornecedorService;

    @Transactional
    public Compra cadastrarCompra(CompraDto compraDto) {
        Compra compra = new Compra();
        Item item = itemService.buscaItem(compraDto.getIdItem(), null);

        compra.setDataCompra(compraDto.getDataCompra());
        compra.setFornecedor(fornecedorService.buscaFornecedor(compraDto.getIdFornecedor(), null));
        compra.setItem(item);
        compra.setQuantidadeItens(compraDto.getQuantidade());
        compra.setValorTotal(calculaValorTotal(compra.getQuantidadeItens(), compra.getItem(), compra.getFornecedor()));

        item.setQuantidadeEstoque(item.getQuantidadeEstoque() + compraDto.getQuantidade());
        itemRepository.save(item);

        return compraRepository.save(compra);
    }

    private Double calculaValorTotal(Integer quantidadeItens, Item item, Fornecedor fornecedor) {
        return quantidadeItens * fornecedorService.buscaItem(item, fornecedor).getValorItem();
    }

    public PdfDto gerarRelatorioPDF(LocalDate dataInicial, LocalDate dataFinal, Long idFornecedor) throws DocumentException {
        List<Compra> relatorio;
        List<ComprasResponseDto> relatorioResponse = new ArrayList<>();
        if (dataInicial.isAfter(dataFinal)) {
            throw new ErroDeNegocio("Data inicial deve ser menor que data final");
        }

        if (idFornecedor == null) {
            relatorio = compraRepository.findByDataCompraBetween(dataInicial, dataFinal);
        } else {
            relatorio = compraRepository.findByDataCompraBetweenAndFornecedor(dataInicial, dataFinal, fornecedorService.buscaFornecedor(idFornecedor, null));
        }

        relatorio.forEach(compra ->
                relatorioResponse.add(ComprasResponseDto.builder()
                        .nomeItem(compra.getItem().getNome())
                        .nomeFornecedor(compra.getFornecedor().getNome())
                        .valorTotal(compra.getValorTotal())
                        .quantidade(compra.getQuantidadeItens())
                        .data(compra.getDataCompra())
                        .build()));

        String fornecedor = idFornecedor == null ? null : relatorioResponse.get(0).getNomeFornecedor();
        return new PdfDto(relatorioPDF(relatorioResponse, dataInicial, dataFinal, fornecedor).toByteArray());
    }

    private ByteArrayOutputStream relatorioPDF(List<ComprasResponseDto> relatorioResponse, LocalDate dataInicial, LocalDate dataFinal, String nomeFornecedor) throws DocumentException, DocumentException {
        Document document = new Document();
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, pdfStream);
        document.open();
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(192, 192, 192));

        Paragraph titulo = new Paragraph("Controle Total", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
        Paragraph subtitulo = new Paragraph("Relatório de compras", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));

        titulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(subtitulo);

        document.add(Chunk.NEWLINE);

        String situacao = nomeFornecedor == null ? "Compras" : "Compras do fornecedor " + nomeFornecedor;
        situacao += " no período de "
                + dataInicial.getDayOfMonth() + "/" + dataInicial.getMonthValue() + "/" + dataInicial.getYear() + " a "
                + dataFinal.getDayOfMonth() + "/" + dataFinal.getMonthValue() + "/" + dataFinal.getYear() + ":";
        document.add(new Paragraph(situacao, new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));

        for (ComprasResponseDto dto : relatorioResponse) {
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Nome do Item: " + dto.getNomeItem()));
            document.add(new Paragraph("Nome do Fornecedor: " + dto.getNomeFornecedor()));
            document.add(new Paragraph("Quantidade: " + dto.getQuantidade()));
            document.add(new Paragraph("Data: " + dto.getData()));
            document.add(new Paragraph("Valor total: " + dto.getValorTotal()));

            document.add(Chunk.NEWLINE);
            document.add(new Chunk(lineSeparator));
        }

        document.close();
        return pdfStream;
    }
}
