package com.controletotal.controletotal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_compra", schema = "controletotal")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_COMPRA")
    @SequenceGenerator(sequenceName = "SEQ_COMPRA_OID", allocationSize = 1, name = "SEQ_COMPRA")
    @Column(name = "id_compra")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_item", referencedColumnName = "id_item")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @Column(name = "qtd_item")
    private Integer quantidadeItens;

    @Column(name = "dt_compra")
    private LocalDate dataCompra;

    @Column(name = "vl_total")
    private Double valorTotal;
}
