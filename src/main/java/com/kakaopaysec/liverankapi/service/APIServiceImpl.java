package com.kakaopaysec.liverankapi.service;

import com.kakaopaysec.liverankapi.common.CommonUtils;
import com.kakaopaysec.liverankapi.domain.dto.GetStockInfoParamsDTO;
import com.kakaopaysec.liverankapi.domain.dto.StockInfoDTO;
import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.domain.repository.StockDetailRepository;
import com.kakaopaysec.liverankapi.domain.repository.StockInfoRepository;
import com.kakaopaysec.liverankapi.domain.repository.StockItemRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class APIServiceImpl implements APIService{

    private final StockDetailRepository stockDetailRepository;
    private final StockInfoRepository stockInfoRepository;
    private final CommonUtils commonUtils;

    public APIServiceImpl(StockDetailRepository stockDetailRepository, StockInfoRepository stockInfoRepository, CommonUtils commonUtils) {
        this.stockDetailRepository = stockDetailRepository;
        this.stockInfoRepository = stockInfoRepository;
        this.commonUtils = commonUtils;
    }

    @Override
    public Flux<StockInfoDTO> getStockInfos(GetStockInfoParamsDTO request) {
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String tag = request.getTag();
        String sortOrder = request.getSortOrder();
        return stockInfoRepository.findAllWithPagingAndSorting(pageable, tag, sortOrder);
    }

    @Override
    public Flux<StockDetail> updateStockDetails() {
        return stockDetailRepository.findAll()
                .map(this::processStockDetails)
                .flatMap(stockDetailRepository::save);
    }

    private StockDetail processStockDetails(StockDetail stockDetail) {
        StockDetail detail = StockDetail.builder()
                .id(stockDetail.getId())
                .itemId(stockDetail.getItemId())
                .previousPrice(stockDetail.getPrice())
                .price(commonUtils.generateRandomInt((int) (stockDetail.getPrice() * 0.7), (int) (stockDetail.getPrice() * 1.3), commonUtils.getTickSize(stockDetail.getPrice())))
                .volume(commonUtils.generateRandomInt(1, 1000000000, 1))
                .hitCount(commonUtils.generateRandomInt(1, 100000000, 1))
                .build();
        return detail;
    }
}
