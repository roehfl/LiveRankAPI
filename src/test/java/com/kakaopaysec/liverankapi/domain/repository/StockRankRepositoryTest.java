package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.dto.StockInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureWebTestClient
public class StockRankRepositoryTest {

    @Autowired
    StockRankRepository stockRankRepository;

    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void testBuildQuery(int tag, int nextOffset, int size, String expected) {
        String actual = buildQuery(tag, nextOffset, size);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFindAllWithPagingAndSorting() {
        Flux<StockInfoDTO> stockInfoDTOFlux = stockRankRepository.findAllWithPagingAndSorting(0, 10, 1);

        StepVerifier.create(stockInfoDTOFlux)
                .expectNextCount(10)
                .verifyComplete();

        AtomicReference<StockInfoDTO> previous = new AtomicReference<>();

        StepVerifier.create(this.stockRankRepository.findAllWithPagingAndSorting(0, 10, 1))
                .thenConsumeWhile(current -> {
                    boolean isOrdered = previous.get() == null
                            || previous.get().getPrice() <= current.getPrice();
                    previous.set(current);
                    return isOrdered;
                })
                .thenCancel()
                .verify();
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(1, 0, 10, "SELECT * FROM (SELECT code, name, price, previous_price, price_diff, price_diff_percentage, hit_count, volume FROM stock_item " +
                        "LEFT JOIN stock_detail ON stock_item.id = stock_detail.item_id) as temp_table " +
                        "ORDER BY hit_count DESC LIMIT 10 OFFSET 0"),
                Arguments.of(2, 100, 15, "SELECT * FROM (SELECT code, name, price, previous_price, price_diff, price_diff_percentage, hit_count, volume FROM stock_item " +
                        "LEFT JOIN stock_detail ON stock_item.id = stock_detail.item_id) as temp_table " +
                        "ORDER BY price_diff_percentage DESC LIMIT 15 OFFSET 100"),
                Arguments.of(3, 0, 20, "SELECT * FROM (SELECT code, name, price, previous_price, price_diff, price_diff_percentage, hit_count, volume FROM stock_item " +
                        "LEFT JOIN stock_detail ON stock_item.id = stock_detail.item_id) as temp_table " +
                        "ORDER BY price_diff_percentage ASC LIMIT 20 OFFSET 0"),
                Arguments.of(4, 30, 25, "SELECT * FROM (SELECT code, name, price, previous_price, price_diff, price_diff_percentage, hit_count, volume FROM stock_item " +
                        "LEFT JOIN stock_detail ON stock_item.id = stock_detail.item_id) as temp_table " +
                        "ORDER BY volume DESC LIMIT 25 OFFSET 30")
        );
    }

    private String buildQuery(int tag, int nextOffset, int size) {
        String sortOrder = "";
        String orderBy = "";

        sortOrder = switch (tag) {
            case 1 -> {
                orderBy = "hit_count";
                yield "DESC";
            }
            case 2 -> {
                orderBy = "price_diff_percentage";
                yield "DESC";
            }
            case 3 -> {
                orderBy = "price_diff_percentage";
                yield "ASC";
            }
            case 4 -> {
                orderBy = "volume";
                yield "DESC";
            }
            default -> sortOrder;
        };
        return "SELECT * FROM (SELECT code, name, price, previous_price, price_diff, price_diff_percentage, hit_count, volume FROM stock_item " +
                "LEFT JOIN stock_detail ON stock_item.id = stock_detail.item_id) as temp_table " +
                "ORDER BY " + orderBy + " " + sortOrder +
                " LIMIT " + size + " OFFSET " + nextOffset;
    }

}
