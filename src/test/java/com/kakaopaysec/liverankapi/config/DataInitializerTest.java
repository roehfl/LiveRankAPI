package com.kakaopaysec.liverankapi.config;

import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.domain.entity.StockItem;
import com.kakaopaysec.liverankapi.domain.repository.StockDetailRepository;
import com.kakaopaysec.liverankapi.domain.repository.StockItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DataInitializerTest {
    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private StockDetailRepository stockDetailRepository;

    @Autowired
    private DataInitializer dataInitializer;

    @Test
    void testLoadData() throws Exception {
        dataInitializer.loadData().run();

        Flux<StockItem> stockItemFlux = stockItemRepository.findAll();
        Flux<StockDetail> stockDetailFlux = stockDetailRepository.findAll();

        StepVerifier.create(stockItemFlux)
                .expectNextMatches(stockItem ->
                        stockItem != null && stockItem.getId() != null && stockItem.getCode() != null && stockItem.getName() != null)
                .expectNextCount(119)
                .verifyComplete();

        StepVerifier.create(stockItemFlux)
                .expectNextCount(120)
                .verifyComplete();

        StepVerifier.create(stockDetailFlux)
                .expectNextMatches(stockDetail ->
                        stockDetail != null && stockDetail.getId() != null && stockDetail.getItemId() != null)
                .expectNextCount(119)
                .verifyComplete();

        StepVerifier.create(stockDetailFlux)
                .expectNextCount(120)
                .verifyComplete();

    }

}