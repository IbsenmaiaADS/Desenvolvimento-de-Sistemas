package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.FornecedorDto;
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

        if(nome != null) {
            fornecedor.setNome(nome);
        }

        if(numTelefone != null) {
            fornecedor.setNumTelefone(numTelefone);
        }
        return fornecedorRepository.save(fornecedor);
    }

    public void deletaFornecedor(Long id) {
        fornecedorRepository.deleteById(id);
    }
}
