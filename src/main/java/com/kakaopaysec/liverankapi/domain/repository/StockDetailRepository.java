package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDetailRepository extends ReactiveCrudRepository<StockDetail, Long> {
}
