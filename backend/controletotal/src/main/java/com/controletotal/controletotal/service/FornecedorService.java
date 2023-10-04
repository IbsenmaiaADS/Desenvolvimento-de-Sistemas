package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.FornecedorDto;
import com.controletotal.controletotal.dto.ItemDto;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public List<Fornecedor> buscaTodosOsFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor buscaFornecedor(Long id, String nome) {
        if (id != null) {
            return fornecedorRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Fornecedor não encontrado com o ID: " + id));
        } else if (nome != null) {
            return fornecedorRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Fornecedor não encontrado com o nome: " + nome));
        }
        throw new ErroDeNegocio("Fornecedor não encontrado");
    }

    public Fornecedor cadastraItem(FornecedorDto fornecedorDto) {
        if (fornecedorRepository.findByNomeIgnoreCase(fornecedorDto.getNome()).isPresent()) {
            throw new ErroDeNegocio("Já existe um fornecedor com o mesmo nome");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorDto.getNome());
        fornecedor.setNumTelefone(fornecedorDto.getNumTelefone());

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizaItem(Long idItem, ItemDto itemDto) {
        Fornecedor fornecedor = fornecedorRepository.findById(idItem)
                .orElseThrow(() -> new ErroDeNegocio("Item não encontrado com o ID: " + idItem));

        if (!fornecedor.getNome().equals(itemDto.getNome()) && itemRepository.findByNomeIgnoreCase(itemDto.getNome()).isPresent()) {
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
