package com.controletotal.controletotal.service;

import com.controletotal.controletotal.dto.CompraDto;
import com.controletotal.controletotal.entity.Compra;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompraService {
    private final CompraRepository compraRepository;
    private final ItemService itemService;
    private final FornecedorService fornecedorService;

    public void cadastrarCompra(CompraDto compraDto) {
        Compra compra = new Compra();
        compra.setDataCompra(compraDto.getDataCompra());
        compra.setFornecedor(fornecedorService.buscaFornecedor(compraDto.getIdFornecedor(), null));
        compra.setItem(itemService.buscaItem(compraDto.getIdItem(), null));
        compra.setQuantidadeItens(compra.getQuantidadeItens());
        compra.setValorTotal(calculaValorTotal(compra.getQuantidadeItens(), compra.getItem(), compra.getFornecedor()));

        compraRepository.save(compra);
    }

    private Double calculaValorTotal(Integer quantidadeItens, Item item, Fornecedor fornecedor) {
        return quantidadeItens * fornecedorService.buscaItem(item, fornecedor).getValorItem();
    }
}
