package com.controletotal.controletotal.repository;

import com.controletotal.controletotal.entity.Compra;
import com.controletotal.controletotal.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    @Query("select c from Compra c where c.dataCompra between ?1 and ?2 and c.fornecedor = ?3")
    List<Compra> findByDataCompraBetweenAndFornecedor(LocalDate dataCompraStart, LocalDate dataCompraEnd, Fornecedor fornecedor);
    @Query("select c from Compra c where c.dataCompra between ?1 and ?2")
    List<Compra> findByDataCompraBetween(LocalDate dataCompraStart, LocalDate dataCompraEnd);

}

