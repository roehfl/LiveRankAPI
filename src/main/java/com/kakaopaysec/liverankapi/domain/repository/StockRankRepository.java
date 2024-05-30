package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.dto.StockInfoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

public interface StockRankRepository {
    Flux<StockInfoDTO> findAllWithPagingAndSorting(int pageNumber, int pageSize, String tag, Sort.Direction sortOrder);
}
