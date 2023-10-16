package com.controletotal.controletotal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_item", schema = "controletotal")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_ITEM")
    @SequenceGenerator(sequenceName = "SEQ_ITEM_OID", allocationSize = 1, name = "SEQ_ITEM")
    @Column(name = "id_item")
    private Long id;

    @Column(name = "nm_item")
    private String nome;

    @Column(name = "qtd_estoque")
    private Integer quantidadeEstoque;
}
