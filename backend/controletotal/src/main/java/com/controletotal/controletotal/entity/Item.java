package com.controletotal.controletotal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<ItemFornecedor> fornecedores;
}
