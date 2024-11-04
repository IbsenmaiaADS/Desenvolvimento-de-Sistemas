package com.controletotal.controletotal.service;

import com.controletotal.controletotal.entity.Compra;
import com.controletotal.controletotal.repository.CompraRepository;
import com.controletotal.controletotal.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.controletotal.controletotal.util.TestDataBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemService itemService;

    @Mock
    private FornecedorService fornecedorService;

    @InjectMocks
    private CompraService compraService;

    @Test
    void deveCadastrarCompra() {

        Mockito.when(itemService.buscaItem(1L, null))
                .thenReturn(buildItem());

        Mockito.when(fornecedorService.buscaFornecedor(1L, null))
                .thenReturn(buildFornecedor());

        Mockito.when(fornecedorService.buscaItem(buildItem(), buildFornecedor()))
                .thenReturn(buildItemFornecedor());

        compraService.cadastrarCompra(buildCompraDto());

        verify(itemRepository, times(1)).save(buildItemAtualizado());
        verify(compraRepository, times(1)).save(any(Compra.class));
    }

}