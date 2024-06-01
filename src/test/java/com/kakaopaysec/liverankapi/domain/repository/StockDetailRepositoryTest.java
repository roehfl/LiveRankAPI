package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.domain.entity.StockItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class StockDetailRepositoryTest {

    @Autowired
    private StockDetailRepository stockDetailRepository;

    @Test
    public void testSaveAndFind() {
        StockDetail stockDetail = StockDetail.builder()
                .id(1L)
                .price(10)
                .itemId(1L)
                .hitCount(10)
                .priceDiff(10)
                .priceDiffPercentage(10)
                .volume(10).build();

        stockDetailRepository.save(stockDetail)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        Mono<StockDetail> foundStockDetail = stockDetailRepository.findById(1L);

        StepVerifier.create(foundStockDetail)
                .expectNextMatches(found -> found.getId() == stockDetail.getId() && stockDetail.getPrice() == found.getPrice()
                        && stockDetail.getItemId() == found.getItemId() && stockDetail.getVolume() == found.getVolume() && stockDetail.getHitCount() == found.getHitCount()
                        && stockDetail.getPriceDiff() == found.getPriceDiff() && stockDetail.getPriceDiffPercentage() == found.getPriceDiffPercentage()
                )
                .verifyComplete();
    }
}
