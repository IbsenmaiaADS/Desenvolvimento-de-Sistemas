package com.controletotal.controletotal.service;

import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.controletotal.controletotal.util.TestDataBuilder.buildFornecedor;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Test
    void erroBuscaFornecedorNaoEncontrado() {
        Long idNaoExistente = 999L;

        Mockito.when(fornecedorRepository.findById(idNaoExistente)).thenReturn(Optional.empty());

        assertThrows(ErroDeNegocio.class, () -> fornecedorService.buscaFornecedor(idNaoExistente, null));
    }

    @Test
    void buscaFornecedorSucesso() {
        Long idExistente = 1L;

        Mockito.when(fornecedorRepository.findById(idExistente)).thenReturn(Optional.of(buildFornecedor()));

        assertDoesNotThrow(() -> fornecedorService.buscaFornecedor(idExistente, null));
        verify(fornecedorRepository, times(1)).findById(idExistente);
    }
}