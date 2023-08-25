package com.controletotal.controletotal.repository;

import com.controletotal.controletotal.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByNomeIgnoreCase(String nome);

}
