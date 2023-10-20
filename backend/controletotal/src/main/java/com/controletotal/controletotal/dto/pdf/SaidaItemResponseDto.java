package com.controletotal.controletotal.dto.pdf;

import com.controletotal.controletotal.entity.SaidaItem;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class SaidaItemResponseDto {
    private String nomeItem;
    private Integer quantidade;
    private LocalDate data;


    public static SaidaItemResponseDto toSaidaItemResponseDto (SaidaItem saidaItem) {
        return SaidaItemResponseDto.builder()
                .nomeItem(saidaItem.getItem().getNome())
                .quantidade(saidaItem.getQuantidadeSaida())
                .data(saidaItem.getDataAtualizacao())
                .build();
    }
}
