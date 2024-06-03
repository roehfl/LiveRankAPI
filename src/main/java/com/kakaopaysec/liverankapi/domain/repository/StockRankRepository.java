package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.dto.StockInfoDTO;
import reactor.core.publisher.Flux;

public interface StockRankRepository {
    Flux<StockInfoDTO> findAllWithPagingAndSorting(int nextOffset, int pageSize, int tag);
}
