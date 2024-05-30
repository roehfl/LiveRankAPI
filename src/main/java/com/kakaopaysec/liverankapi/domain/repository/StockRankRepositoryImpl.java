package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.domain.dto.StockInfoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Map;

@Repository
public class StockRankRepositoryImpl implements StockRankRepository {
    private final DatabaseClient databaseClient;

    public StockRankRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<StockInfoDTO> findAllWithPagingAndSorting(int pageNumber, int pageSize, String tag, Sort.Direction sortOrder) {
        String query = buildQuery(tag, sortOrder, pageNumber, pageSize);
        return databaseClient.sql(query)
                .map((row, metadata) -> StockInfoDTO.builder()
                        .code(row.get("code", String.class))
                        .name(row.get("name", String.class))
                        .price(row.get("price", Integer.class))
                        .priceDiff(row.get("price_diff", Integer.class))
                        .previousPrice(row.get("previous_price", Integer.class))
                        .priceDiffPercent(Math.round(row.get("price_diff_percentage", Double.class) * 100.0) / 100.0)
                        .hitCount(row.get("hit_count", Integer.class))
                        .volume(row.get("volume", Integer.class)).build()
                )
                .all();
    }

    //아.... order by에 price_diff_percent가 안먹는다 데이터로 저장해야겠다
    private String buildQuery(String tag, Sort.Direction sortOrder, int page, int size) {
        return "SELECT * FROM (SELECT code, name, price, previous_price, price_diff, price_diff_percentage, hit_count, volume FROM stock_item " +
                "LEFT JOIN stock_detail ON stock_item.id = stock_detail.item_id) as temp_table " +
                "ORDER BY " + tag + " " + sortOrder.toString() +
                " LIMIT " + size + " OFFSET " + page * size;
    }


}
