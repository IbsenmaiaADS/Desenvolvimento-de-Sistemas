package com.controletotal.controletotal.dto;

import com.controletotal.controletotal.entity.ItemFornecedor;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ItensFornecedorDto {
    private String nomeItem;
    private Double valorItem;

    public static ItensFornecedorDto toItensFornecedorDto(ItemFornecedor itemFornecedor) {
        return ItensFornecedorDto.builder()
                .nomeItem(itemFornecedor.getItem().getNome())
                .valorItem(itemFornecedor.getValorItem())
                .build();
    }
}
