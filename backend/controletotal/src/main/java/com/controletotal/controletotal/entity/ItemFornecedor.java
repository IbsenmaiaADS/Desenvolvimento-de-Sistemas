package com.controletotal.controletotal.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_item_fornecedor", schema = "controletotal")
public class ItemFornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_ITEM_FORNECEDOR")
    @SequenceGenerator(sequenceName = "SEQ_ITEM_FORNECEDOR_OID", allocationSize = 1, name = "SEQ_ITEM_FORNECEDOR")
    @Column(name = "id_item_fornecedor")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id_fornecedor")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "id_item", referencedColumnName = "id_item")
    private Item item;

    @Column(name = "vl_item")
    private Double valorItem;
}
