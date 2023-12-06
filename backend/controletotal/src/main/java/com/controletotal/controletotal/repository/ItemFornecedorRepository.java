package com.controletotal.controletotal.repository;

import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.ItemFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemFornecedorRepository extends JpaRepository<ItemFornecedor, Long> {

    Optional<ItemFornecedor> findByItemAndFornecedor(Item item, Fornecedor fornecedor);
    
}
