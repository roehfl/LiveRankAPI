package com.kakaopaysec.liverankapi.service;

import com.kakaopaysec.liverankapi.common.CommonUtils;
import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.domain.repository.StockDetailRepository;
import com.kakaopaysec.liverankapi.domain.repository.StockItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class APIServiceImpl implements APIService{

    private final StockDetailRepository stockDetailRepository;
    private final StockItemRepository stockItemRepository;
    private final CommonUtils commonUtils;

    public APIServiceImpl(StockDetailRepository stockDetailRepository, StockItemRepository stockItemRepository, CommonUtils commonUtils) {
        this.stockDetailRepository = stockDetailRepository;
        this.stockItemRepository = stockItemRepository;
        this.commonUtils = commonUtils;
    }

    @Override
    public ServerResponse getStockRanking(ServerRequest request) {
        return null;
    }

    @Override
    public Flux<StockDetail> updateStockDetails() {
        return stockDetailRepository.findAll()
                .map(this::processStockDetails)
                .flatMap(stockDetailRepository::save);
    }

    private StockDetail processStockDetails(StockDetail stockDetail) {
        return StockDetail.builder()
                        .previousPrice(stockDetail.getPrice())
                        .price(commonUtils.generateRandomInt(stockDetail.getPrice(), (int) (stockDetail.getPrice() * 1.3), commonUtils.getTickSize(stockDetail.getPrice())))
                        .volume(commonUtils.generateRandomInt(1, 1000000000, 1))
                        .hitCount(commonUtils.generateRandomInt(1, 100000000, 1))
                        .build();
    }
}
