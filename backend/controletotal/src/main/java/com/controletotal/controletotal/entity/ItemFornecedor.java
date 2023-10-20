package com.controletotal.controletotal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_item_fornecedor", schema = "controletotal")
public class ItemFornecedor {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id_fornecedor")
    private Fornecedor fornecedor;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_item", referencedColumnName = "id_item")
    private Item item;

    @Column(name = "vl_item")
    private Double valorItem;
}
