package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.domain.entity.StockItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StockItemRepository extends ReactiveCrudRepository<StockItem, Long> {

}
