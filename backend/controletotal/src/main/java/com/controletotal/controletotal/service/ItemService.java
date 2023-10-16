package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.ItemDto;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> buscaTodosOsItens() {
        return itemRepository.findAll();
    }

    public Item buscaItem(Long id, String nome) {
        validaBuscaItem(id, nome);

        if (id != null) {
            return itemRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o ID: " + id));
        } else {
            return itemRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o nome: " + nome));
        }
    }

    public Item cadastraItem(ItemDto itemDto) {
        if (itemRepository.findByNomeIgnoreCase(itemDto.getNome()).isPresent()) {
            throw new ErroDeNegocio("Já existe um item com o mesmo nome");
        }

        Item item = new Item();
        item.setNome(itemDto.getNome());
        item.setQuantidadeEstoque(itemDto.getQuantidadeEstoque());

        return itemRepository.save(item);
    }

    @Transactional
    public Item atualizaItem(Long idItem, Integer quantidadeEstoque, String nome) {
        validaEdicao(quantidadeEstoque, nome);

        Item item = itemRepository.findById(idItem)
                .orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o ID: " + idItem));

        if (!item.getNome().equals(nome) && itemRepository.findByNomeIgnoreCase(nome).isPresent()) {
            throw new ErroDeNegocio("Já existe um item com o mesmo nome");
        }

        if (nome != null) {
            item.setNome(nome);
        }

        if (quantidadeEstoque != null) {
            item.setQuantidadeEstoque(quantidadeEstoque);
        }

        return itemRepository.save(item);
    }

    public void deletaItem(Long idItem) {
        itemRepository.deleteById(idItem);
    }


    private void validaBuscaItem(Long id, String nome) {
        if (id == null && nome == null) {
            throw new ErroDeNegocio("Nenhuma solicitação especificada. Informe id ou nome para buscar");
        }
    }

    private void validaEdicao(Integer quantidadeEstoque, String nome) {
        if (quantidadeEstoque == null && nome == null) {
            throw new ErroDeNegocio("Nenhuma solicitação de edição especificada. Informe quantidade em estoque ou nome para atualizar");
        }
    }
}
