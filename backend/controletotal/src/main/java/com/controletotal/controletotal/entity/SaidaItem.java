package com.controletotal.controletotal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_saida_item", schema = "controletotal")
public class SaidaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_SAIDA")
    @SequenceGenerator(sequenceName = "SEQ_SAIDA_OID", allocationSize = 1, name = "SEQ_SAIDA")
    @Column(name = "id_saida")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tb_item")
    private Item item;

    @Column(name = "qtd_saida_estoque")
    private Integer quantidadeSaida;

    @Column(name = "dt_saida")
    private LocalDate dataSaida;

    //adicionar referencia do usuario
}
