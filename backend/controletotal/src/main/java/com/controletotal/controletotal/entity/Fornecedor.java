package com.controletotal.controletotal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_fornecedor", schema = "controletotal")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fornecedor")
    private Long id;

    @Column(name = "nm_fornecedor")
    private String nome;

    @Column(name = "nu_telefone")
    private String numTelefone;
}
