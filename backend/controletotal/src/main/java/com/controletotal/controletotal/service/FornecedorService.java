package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.FornecedorDto;
import com.controletotal.controletotal.dto.ItensFornecedorDto;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.ItemFornecedor;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.FornecedorRepository;
import com.controletotal.controletotal.repository.ItemFornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.controletotal.controletotal.dto.ItensFornecedorDto.toItensFornecedorDto;

@Service
@RequiredArgsConstructor
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;
    private final ItemFornecedorRepository itemFornecedorRepository;

    public List<Fornecedor> buscaTodosOsFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor buscaFornecedor(Long id, String nome) {
        validaBuscaFornecedor(id, nome);

        if (id != null) {
            return fornecedorRepository.findById(id).orElseThrow(() -> new ErroDeNegocio("Fornecedor não encontrado com o ID: " + id));
        } else {
            return fornecedorRepository.findByNomeIgnoreCase(nome).orElseThrow(() -> new ErroDeNegocio("Fornecedor não encontrado com o nome: " + nome));
        }
    }

    public Fornecedor cadastraFornecedor(FornecedorDto fornecedorDto) {
        if (fornecedorRepository.findByNomeIgnoreCase(fornecedorDto.getNome()).isPresent()) {
            throw new ErroDeNegocio("Já existe um fornecedor com o mesmo nome");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorDto.getNome());
        fornecedor.setNumTelefone(fornecedorDto.getNumTelefone());

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizaFornecedor(Long id, String nome, String numTelefone) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new ErroDeNegocio("Fornecedor não encontrado com o ID: " + id));

        if (fornecedor.getNome().equals(nome) && fornecedorRepository.findByNomeIgnoreCase(nome).isPresent()) {
            throw new ErroDeNegocio("Já existe um fornecedor com o mesmo nome");
        }

        if (nome != null) {
            fornecedor.setNome(nome);
        }

        if (numTelefone != null) {
            fornecedor.setNumTelefone(numTelefone);
        }
        return fornecedorRepository.save(fornecedor);
    }

    public void deletaFornecedor(Long id) {
        fornecedorRepository.deleteById(id);
    }

    public List<ItensFornecedorDto> buscarItens(Long id) {
        List<ItensFornecedorDto> itens = new ArrayList<>();

        buscaFornecedor(id, null).getItensFornecidos()
                .forEach(itemFornecedor -> itens.add(toItensFornecedorDto(itemFornecedor)));
        return itens;
    }

    public ItemFornecedor buscaItem(Item item, Fornecedor fornecedor) {
        return itemFornecedorRepository.findByItemAndFornecedor(item, fornecedor)
                .orElseThrow(() -> new ErroDeNegocio("Item não encontrado para o fornecedor"));
    }

    private void validaBuscaFornecedor(Long id, String nome) {
        if (id == null && nome == null) {
            throw new ErroDeNegocio("Nenhuma solicitação especificada. Informe id ou nome para buscar");
        }
    }
}
