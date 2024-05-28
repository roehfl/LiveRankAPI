package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.entity.StockItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemsRepository extends JpaRepository<StockItems, Long> {
}
