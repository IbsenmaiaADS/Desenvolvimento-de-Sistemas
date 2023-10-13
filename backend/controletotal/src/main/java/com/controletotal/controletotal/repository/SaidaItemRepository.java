package com.controletotal.controletotal.repository;

import com.controletotal.controletotal.entity.SaidaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaidaItemRepository extends JpaRepository<SaidaItem, Long> {
    List<SaidaItem> findBySituacaoSaida(int situacao);
}
