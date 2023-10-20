package com.controletotal.controletotal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<ItemFornecedor> itensFornecidos;
}
