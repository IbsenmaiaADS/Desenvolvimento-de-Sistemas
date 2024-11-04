package com.controletotal.controletotal.util;

import com.controletotal.controletotal.dto.CompraDto;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.ItemFornecedor;

import java.time.LocalDate;

public class TestDataBuilder {

    public static Item buildItemAtualizado() {
        Item item = buildItem();
        item.setQuantidadeEstoque(7);

        return item;
    }

    public static CompraDto buildCompraDto() {
        CompraDto compraDto = new CompraDto();
        compraDto.setIdItem(1L);
        compraDto.setIdFornecedor(1L);
        compraDto.setQuantidade(2);
        compraDto.setDataCompra(LocalDate.now());

        return compraDto;
    }

    public static Fornecedor buildFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setNome("Papelaria LTDA");
        fornecedor.setNumTelefone("999999999");

        return fornecedor;
    }

    public static ItemFornecedor buildItemFornecedor() {
        ItemFornecedor itemFornecedor = new ItemFornecedor();
        itemFornecedor.setValorItem(10.0);

        return itemFornecedor;
    }

    public static Item buildItem() {
        Item item = new Item();
        item.setId(1L);
        item.setNome("Caneta");
        item.setQuantidadeEstoque(5);

        return item;
    }
}
