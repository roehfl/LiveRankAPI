package com.kakaopaysec.liverankapi.config;

import com.kakaopaysec.liverankapi.domain.entity.StockDetail;
import com.kakaopaysec.liverankapi.domain.entity.StockItem;
import com.kakaopaysec.liverankapi.domain.repository.StockDetailRepository;
import com.kakaopaysec.liverankapi.domain.repository.StockItemRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.*;

@Configuration
public class DataInitializer {

    private final StockItemRepository stockItemRepository;
    private final StockDetailRepository stockDetailRepository;

    public DataInitializer(StockItemRepository stockItemRepository, StockDetailRepository stockDetailRepository) {
        this.stockItemRepository = stockItemRepository;
        this.stockDetailRepository = stockDetailRepository;
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            for (CSVRecord csvRecord : csvParser()) {
                String code = csvRecord.get("code");
                String name = csvRecord.get("name");
                int price = Integer.parseInt(csvRecord.get("price"));

                StockItem stockItem = StockItem.builder()
                                        .code(code)
                                        .name(name)
                                        .build();

                stockItemRepository.save(stockItem)
                        .flatMap(item -> {
                            StockDetail stockDetail = StockDetail.builder()
                                    .price(price)
                                    .itemId(item.getId())
                                    .build();
                            return stockDetailRepository.save(stockDetail);
                        }).subscribe();
            }
        };
    }

    @Bean
    public Reader csvFileReader() throws IOException {
        return new InputStreamReader(new ClassPathResource("SampleData.csv").getInputStream());
    }

    @Bean
    public CSVParser csvParser() throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
        return new CSVParser(csvFileReader(), csvFormat);
    }
}
