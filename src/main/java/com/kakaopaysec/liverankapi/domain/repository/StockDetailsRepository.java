package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.entity.StockDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDetailsRepository extends JpaRepository<StockDetails, Long> {
}
