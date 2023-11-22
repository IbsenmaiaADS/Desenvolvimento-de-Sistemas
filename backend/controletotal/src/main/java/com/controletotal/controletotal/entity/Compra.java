package com.controletotal.controletotal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_compra", schema = "controletotal")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long idCompra;

    @Column(name = "nm_fornecedor")
    private String nomeFornecedo;

    @Column(name = "nm_item")
    private String nomeItem;

    @Column (name = "qtd_item")
    private Integer qtdItem;

    @Column (name = "data_compra")
    private Date dataCompra;
}
