package com.controletotal.controletotal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_item", schema = "controletotal")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Long id;

    @Column(name = "nm_item")
    private String nome;

    @Column(name = "qtd_estoque")
    private Integer quantidadeEstoque;
}
