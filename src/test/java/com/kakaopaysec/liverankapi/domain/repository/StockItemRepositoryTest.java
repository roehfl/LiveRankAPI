package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.entity.StockItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class StockItemRepositoryTest {

    @Autowired
    private StockItemRepository stockItemRepository;

    @Test
    public void testSaveAndFind() {
        StockItem stockItem = StockItem.builder()
                .id(1L)
                .name("test")
                .code("00001").build();

        stockItemRepository.save(stockItem)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        Mono<StockItem> foundStockItem = stockItemRepository.findById(1L);

        StepVerifier.create(foundStockItem)
                .expectNextMatches(found -> found.getName().equals(stockItem.getName()) && found.getCode().equals(stockItem.getCode()) && found.getId().equals(stockItem.getId())
                        )
                .verifyComplete();
    }
}
