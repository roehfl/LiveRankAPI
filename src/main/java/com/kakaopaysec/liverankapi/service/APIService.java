package com.kakaopaysec.liverankapi.service;

import com.kakaopaysec.liverankapi.domain.dto.StockInfoDTO;
import com.kakaopaysec.liverankapi.domain.dto.StockRankParamsDTO;
import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface APIService {
    Flux<StockInfoDTO> getStockInfos(StockRankParamsDTO request);

    Flux<StockDetail> updateStockDetails();
}
