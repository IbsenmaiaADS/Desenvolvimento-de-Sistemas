package com.controletotal.controletotal.service;

import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.SaidaItem;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.SaidaItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
