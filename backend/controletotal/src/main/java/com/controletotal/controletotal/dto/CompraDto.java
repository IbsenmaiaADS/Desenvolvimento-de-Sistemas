package com.controletotal.controletotal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CompraDto {
    @NotNull(message = "Id do fornecedor é obrigatório")
    Long idFornecedor;

    @NotNull(message = "Item é obrigatório")
    Long idItem;

    @NotNull(message = "Quantidade do item é obrigatório")
    Integer quantidade;

    @NotNull(message = "Data da compra é obrigatório")
    LocalDate dataCompra;
}
