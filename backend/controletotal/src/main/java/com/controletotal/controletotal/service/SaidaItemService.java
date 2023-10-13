package com.controletotal.controletotal.service;

import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.SaidaItem;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.SaidaItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.controletotal.controletotal.enums.SituacaoSaidaEnum.AGUARDANDO_APROVACAO;

@Service
@RequiredArgsConstructor
public class SaidaItemService {
    private final ItemService itemService;
    private final SaidaItemRepository saidaItemRepository;

    public List<SaidaItem> buscarItensAguardandoAprovacao() {
        return saidaItemRepository.findBySituacaoSaida(AGUARDANDO_APROVACAO.getCodSituacao());
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

    private void validaQuantidadeRetirada(Integer quantidadeEstoque, Integer quantidadeRetirar) {
        if (quantidadeEstoque < quantidadeRetirar || quantidadeEstoque <= 0) {
            throw new ErroDeNegocio("Quantidade solicitada não disponível");
        }
    }
}
