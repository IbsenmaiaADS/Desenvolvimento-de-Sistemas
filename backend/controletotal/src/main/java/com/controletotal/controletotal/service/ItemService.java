package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.ItemDto;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.ItemFornecedor;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.ItemFornecedorRepository;
import com.controletotal.controletotal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final FornecedorService fornecedorService;
    private final ItemFornecedorRepository itemFornecedorRepository;
    private final ItemRepository itemRepository;

    public List<Item> buscaTodosOsItens() {
        return itemRepository.findAll();
    }

       public List<ItemFornecedor> buscaTodosOsItensRelacionadosComFornecedor() {
        return itemFornecedorRepository.findAll();
    }

    public Item buscaItemPeloNome(String nome) {
        if(nome != null) {
            return itemRepository.findByNome(nome);
        } else {
            throw new ErroDeNegocio("Item não encontrado com o nome: " + nome);
        }
    }
    
    public Item buscaItemPeloId(Long id) {
        if(id != null) {
            return itemRepository.findItemById(id);
        } else {
            throw new ErroDeNegocio("Item não encontrado com o id: " + id);
        }
    }

    public Item buscaItem(Long id, String nome) {
        validaBuscaItem(id, nome);

        if (id != null) {
            return itemRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o ID: " + id));
        } else {
            return itemRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o nome: " + nome));
        }
    }

    @Transactional
    public ItemFornecedor cadastraItem(ItemDto itemDto) {
        Fornecedor fornecedor = fornecedorService.buscaFornecedor(itemDto.getIdFornecedor(), null);
        ItemFornecedor itemFornecedor = new ItemFornecedor();
        itemFornecedor.setFornecedor(fornecedor);
        AtomicReference<Item> itemSalvo = new AtomicReference<>(new Item());

        itemRepository.findByNomeIgnoreCase(itemDto.getNome()).ifPresentOrElse(
                itemSalvo::set,
                () -> {
                    Item item = new Item();
                    item.setNome(itemDto.getNome());
                    item.setQuantidadeEstoque(0);
                    itemSalvo.set(itemRepository.save(item));
                }
        );

        itemFornecedor.setItem(itemSalvo.get());
        itemFornecedor.setValorItem(itemDto.getValor());

        itemFornecedorRepository
                .findByItemAndFornecedor(itemFornecedor.getItem(), itemFornecedor.getFornecedor())
                .ifPresent(itemFornecedor1 -> {
                    throw new ErroDeNegocio("Item já registrado para esse fornecedor. Troque o fornecedor ou o item para continuar");
                });

        return itemFornecedorRepository.save(itemFornecedor);
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
