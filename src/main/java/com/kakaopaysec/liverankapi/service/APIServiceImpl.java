package com.kakaopaysec.liverankapi.service;

import com.kakaopaysec.liverankapi.common.CommonUtils;
import com.kakaopaysec.liverankapi.dto.StockInfoDTO;
import com.kakaopaysec.liverankapi.dto.StockRankParamsDTO;
import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.domain.repository.StockDetailRepository;
import com.kakaopaysec.liverankapi.domain.repository.StockRankRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class APIServiceImpl implements APIService{

    private final StockDetailRepository stockDetailRepository;
    private final StockRankRepository stockRankRepository;
    private final CommonUtils commonUtils;

    public APIServiceImpl(StockDetailRepository stockDetailRepository, StockRankRepository stockRankRepository, CommonUtils commonUtils) {
        this.stockDetailRepository = stockDetailRepository;
        this.stockRankRepository = stockRankRepository;
        this.commonUtils = commonUtils;
    }

    @Override
    public Flux<StockInfoDTO> getStockInfos(StockRankParamsDTO request) {
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String tag = request.getTag();
        Sort.Direction sortOrder = request.getSortOrder();
        return stockRankRepository.findAllWithPagingAndSorting(pageNumber, pageSize, tag, sortOrder);
    }

    @Override
    public Flux<StockDetail> updateStockDetails() {
        return stockDetailRepository.findAll()
                .map(this::processStockDetails)
                .flatMap(stockDetailRepository::save);
    }

    private StockDetail processStockDetails(StockDetail stockDetail) {
        int newPrice = commonUtils.generateRandomInt((int) (stockDetail.getPrice() * 0.7), (int) (stockDetail.getPrice() * 1.3), commonUtils.getTickSize(stockDetail.getPrice()));
        int priceDiff = newPrice - stockDetail.getPrice();
        double priceDiffPercentage = (double) priceDiff / (double) stockDetail.getPrice() * 100;

        StockDetail detail = StockDetail.builder()
                .id(stockDetail.getId())
                .itemId(stockDetail.getItemId())
                .previousPrice(stockDetail.getPrice())
                .price(newPrice)
                .priceDiff(priceDiff)
                .priceDiffPercentage(priceDiffPercentage)
                .volume(commonUtils.generateRandomInt(1, 1000000000, 1))
                .hitCount(commonUtils.generateRandomInt(1, 100000000, 1))
                .build();
        return detail;
    }
}
