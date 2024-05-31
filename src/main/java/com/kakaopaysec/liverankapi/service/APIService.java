package com.kakaopaysec.liverankapi.service;

import com.kakaopaysec.liverankapi.dto.StockInfoDTO;
import com.kakaopaysec.liverankapi.dto.StockRankParamsDTO;
import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import reactor.core.publisher.Flux;

public interface APIService {
    Flux<StockInfoDTO> getStockInfos(StockRankParamsDTO request);

    Flux<StockDetail> updateStockDetails();
}
