package com.kakaopaysec.liverankapi.domain.repository;

import com.kakaopaysec.liverankapi.dto.StockInfoDTO;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class StockRankRepositoryImpl implements StockRankRepository {
    private final DatabaseClient databaseClient;

    public StockRankRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<StockInfoDTO> findAllWithPagingAndSorting(int pageNumber, int pageSize, int tag) {
        String query = buildQuery(tag, pageNumber, pageSize);
        return databaseClient.sql(query)
                .map((row, metadata) -> StockInfoDTO.builder()
                        .code(row.get("code", String.class))
                        .name(row.get("name", String.class))
                        .price(row.get("price", Integer.class))
                        .priceDiff(row.get("price_diff", Integer.class))
                        .priceDiffPercent(Math.round(row.get("price_diff_percentage", Double.class) * 100.0) / 100.0)
                        .hitCount(row.get("hit_count", Integer.class))
                        .volume(row.get("volume", Integer.class)).build()
                )
                .all();
    }

    private String buildQuery(int tag, int page, int size) {
        String sortOrder = "";
        String orderBy = "";

        switch (tag) {
            case 1:
                orderBy = "hit_count";
                sortOrder = "DESC";
                break;
            case 2:
                orderBy = "price_diff_percentage";
                sortOrder = "DESC";
                break;
            case 3:
                orderBy = "price_diff_percentage";
                sortOrder = "ASC";
                break;
            case 4:
                orderBy = "volume";
                sortOrder = "DESC";
        }
        return "SELECT * FROM (SELECT code, name, price, price_diff, price_diff_percentage, hit_count, volume FROM stock_item " +
                "LEFT JOIN stock_detail ON stock_item.id = stock_detail.item_id) as temp_table " +
                "ORDER BY " + orderBy + " " + sortOrder +
                " LIMIT " + size + " OFFSET " + page * size;
    }


}
