package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.ItemDto;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> buscaTodosOsItens() {
        return itemRepository.findAll();
    }

    public Item buscaItem(Long id, String nome) {
        if (id != null) {
            return itemRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o ID: " + id));
        } else if (nome != null) {
            return itemRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o nome: " + nome));
        }
        throw new ErroDeNegocio("Item não encontrado");
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

    private void validaEdicao(Integer quantidadeEstoque, String nome) {
        if (quantidadeEstoque == null && nome == null) {
            throw new ErroDeNegocio("Nenhuma solicitação de edição especificada. Informe quantidade em estoque ou nome para atualizar");
        }
    }

    public ResponseEntity<Item> solicitarItem(Long idItem, Integer quantidade) {

    }
}
