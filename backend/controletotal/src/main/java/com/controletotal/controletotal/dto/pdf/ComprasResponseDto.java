package com.controletotal.controletotal.dto.pdf;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class ComprasResponseDto {
    private String nomeItem;
    private String nomeFornecedor;
    private Integer quantidade;
    private Double valorTotal;
    private LocalDate data;
}
