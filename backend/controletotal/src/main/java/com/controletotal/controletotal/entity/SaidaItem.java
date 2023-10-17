package com.controletotal.controletotal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    @JoinColumn(name = "id_item")
    private Item item;

    @Column(name = "qtd_saida_estoque")
    private Integer quantidadeSaida;

    @Column(name = "dt_atualizacao")
    private LocalDate dataAtualizacao;

    @Column(name = "st_saida")
    private int situacaoSaida;

    //adicionar referencia do usuario solicitante
}
