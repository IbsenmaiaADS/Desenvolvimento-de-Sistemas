package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.ItemDto;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
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

    public Item atualizaItem(Long idItem, ItemDto itemDto) {
        Item item = itemRepository.findById(idItem)
                .orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o ID: " + idItem));

        if (!item.getNome().equals(itemDto.getNome()) && itemRepository.findByNomeIgnoreCase(itemDto.getNome()).isPresent()) {
            throw new ErroDeNegocio("Já existe um item com o mesmo nome");
        }

        item.setNome(itemDto.getNome());
        item.setQuantidadeEstoque(itemDto.getQuantidadeEstoque());

        return itemRepository.save(item);
    }

    public void deletaItem(Long idItem) {
        itemRepository.deleteById(idItem);
    }
}
