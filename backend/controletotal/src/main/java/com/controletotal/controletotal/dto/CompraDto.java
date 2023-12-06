package com.controletotal.controletotal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
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

    public CompraDto(Long idFornecedor, Long idItem, Integer quantidade, LocalDate dataCompra) {
        this.idFornecedor = idFornecedor;
        this.idItem = idItem;
        this.quantidade = quantidade;
        this.dataCompra = dataCompra;
      }
    
    public CompraDto() {
    }

    public Long idFornecedor() {
    return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
    this.idFornecedor = idFornecedor;
    }
    
    public Long idItem() {
    return idItem;
    }

    public void setIdItem(Long idItem) {
    this.idItem = idItem;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

}
