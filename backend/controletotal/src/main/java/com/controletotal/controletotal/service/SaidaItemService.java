package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.pdf.PdfDto;
import com.controletotal.controletotal.dto.pdf.SaidaItemResponseDto;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.SaidaItem;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.SaidaItemRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.controletotal.controletotal.dto.pdf.SaidaItemResponseDto.toSaidaItemResponseDto;
import static com.controletotal.controletotal.enums.SituacaoSaidaEnum.AGUARDANDO_APROVACAO;
import static com.controletotal.controletotal.enums.SituacaoSaidaEnum.APROVADA;
import static com.controletotal.controletotal.enums.SituacaoSaidaEnum.REPROVADA;
import static com.controletotal.controletotal.enums.SituacaoSaidaEnum.getByCodSituacao;

@Service
@RequiredArgsConstructor
public class SaidaItemService {
    private final ItemService itemService;
    private final SaidaItemRepository saidaItemRepository;

    public List<SaidaItem> buscarSolicitacoes(Integer situacaoSaida) {
        if (situacaoSaida == null) {
            return saidaItemRepository.findAll();
        }

        return saidaItemRepository.findBySituacaoSaida(getByCodSituacao(situacaoSaida));
    }

    public SaidaItem solicitarItem(Long idItem, Integer quantidade) {
        Item item = itemService.buscaItem(idItem, null);
        validaQuantidadeRetirada(item.getQuantidadeEstoque(), quantidade);

        SaidaItem saidaItem = new SaidaItem();
        saidaItem.setDataAtualizacao(LocalDate.now());
        saidaItem.setQuantidadeSaida(quantidade);
        saidaItem.setItem(item);
        saidaItem.setSituacaoSaida(AGUARDANDO_APROVACAO.getCodSituacao());
        return saidaItemRepository.save(saidaItem);
    }

    @Transactional
    public SaidaItem aprovarSaidaDeEstoque(Long idSaida) {
        SaidaItem solicitacaoSaida = buscarSolicitacaoSaida(idSaida);

        if (solicitacaoSaida.getSituacaoSaida() == 0) {
            solicitacaoSaida.setDataAtualizacao(LocalDate.now());
            solicitacaoSaida.setSituacaoSaida(APROVADA.getCodSituacao());

            validaQuantidadeRetirada(solicitacaoSaida.getItem().getQuantidadeEstoque(), solicitacaoSaida.getQuantidadeSaida());

            itemService.atualizaItem(solicitacaoSaida.getItem().getId(),
                    solicitacaoSaida.getItem().getQuantidadeEstoque() - solicitacaoSaida.getQuantidadeSaida(), null);

            saidaItemRepository.save(solicitacaoSaida);

            return solicitacaoSaida;
        }

        throw new ErroDeNegocio("A solicitação " + solicitacaoSaida.getId() +
                                " já se encontra " + (solicitacaoSaida.getSituacaoSaida() == 1 ? "aprovada" : "reprovada"));
    }

    public SaidaItem reprovarSaidaDeEstoque(Long idSaida) {
        SaidaItem solicitacaoSaida = buscarSolicitacaoSaida(idSaida);
        if (solicitacaoSaida.getSituacaoSaida() == 0) {
            solicitacaoSaida.setDataAtualizacao(LocalDate.now());
            solicitacaoSaida.setSituacaoSaida(REPROVADA.getCodSituacao());

            saidaItemRepository.save(solicitacaoSaida);

            return solicitacaoSaida;
        }

        throw new ErroDeNegocio("A solicitação " + solicitacaoSaida.getId() +
                                " já se encontra " + (solicitacaoSaida.getSituacaoSaida() == 1 ? "aprovada" : "reprovada"));
    }

    public PdfDto gerarRelatorioPDF(Integer situacaoSaida, LocalDate dataInicial, LocalDate dataFinal) throws DocumentException {
        List<SaidaItem> relatorio;
        List<SaidaItemResponseDto> relatorioResonse = new ArrayList<>();
        if (dataInicial.isAfter(dataFinal)) {
            throw new ErroDeNegocio("Data inicial deve ser menor que data final");
        }

        relatorio = saidaItemRepository.findBySituacaoSaidaAndDataAtualizacaoBetween(getByCodSituacao(situacaoSaida), dataInicial, dataFinal);
        for (SaidaItem saidaItem : relatorio) {
            relatorioResonse.add(toSaidaItemResponseDto(saidaItem));
        }

        return new PdfDto(gerarRelatorioPDF(relatorioResonse, situacaoSaida, dataInicial, dataFinal).toByteArray());
    }

    public ByteArrayOutputStream gerarRelatorioPDF(List<SaidaItemResponseDto> relatorioResponse, Integer situacaoSaida, LocalDate dataInicial, LocalDate dataFinal) throws DocumentException, DocumentException {
        Document document = new Document();
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, pdfStream);
        document.open();
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(192, 192, 192));

        Paragraph titulo = new Paragraph("Controle Total", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
        Paragraph subtitulo = new Paragraph("Relatório de saídas de itens", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
        titulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(subtitulo);

        document.add(Chunk.NEWLINE);

        String situacao = situacaoSaida == 1 ? "Saída aprovadas " : "Saídas reprovadas ";
        situacao += "no período de "
                    + dataInicial.getDayOfMonth() + "/" + dataInicial.getMonthValue() + "/" + dataInicial.getYear() + " a "
                    + dataFinal.getDayOfMonth() + "/" + dataFinal.getMonthValue() + "/" + dataFinal.getYear() + ":";
        document.add(new Paragraph(situacao, new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD)));

        for (SaidaItemResponseDto dto : relatorioResponse) {
            document.add(new Chunk(lineSeparator));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Nome do Item: " + dto.getNomeItem()));
            document.add(new Paragraph("Quantidade: " + dto.getQuantidade()));
            document.add(new Paragraph("Data: " + dto.getData()));

            document.add(Chunk.NEWLINE);
            document.add(new Chunk(lineSeparator));
        }

        document.close();
        return pdfStream;
    }

    private void validaQuantidadeRetirada(Integer quantidadeEstoque, Integer quantidadeRetirar) {
        if (quantidadeEstoque < quantidadeRetirar || quantidadeEstoque <= 0) {
            throw new ErroDeNegocio("Quantidade solicitada não disponível");
        }
    }

    private SaidaItem buscarSolicitacaoSaida(Long idSaida) {
        return saidaItemRepository.findById(idSaida)
                .orElseThrow(() -> new ErroDeNegocio("Solicitação não encontrada com o ID: " + idSaida));
    }
}
