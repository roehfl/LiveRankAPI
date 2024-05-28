package com.kakaopaysec.liverankapi.config;

import com.kakaopaysec.liverankapi.common.CommonUtils;
import com.kakaopaysec.liverankapi.domain.entity.StockDetails;
import com.kakaopaysec.liverankapi.domain.entity.StockItems;
import com.kakaopaysec.liverankapi.domain.repository.StockItemsRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
public class DataInitializer {

    private final StockItemsRepository stockItemsRepository;
    private final CommonUtils commonUtils;

    public DataInitializer(StockItemsRepository stockItemsRepository, CommonUtils commonUtils) {
        this.stockItemsRepository = stockItemsRepository;
        this.commonUtils = commonUtils;
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            List<StockItems> stockItemsList =
                    StreamSupport.stream(csvParser().spliterator(), false).
                            map(csv -> {
                                long id = Long.parseLong(csv.get("id"));
                                String code = csv.get("code");
                                String name = csv.get("name");
                                int price = Integer.parseInt(csv.get("price"));
                                int hitCount = commonUtils.generateRandomInt(1, 100000000, 1);
                                int volume = commonUtils.generateRandomInt(1, 1000000000, 1);

                                StockItems stockItems = new StockItems();
                                StockDetails stockDetails = new StockDetails();
                                stockItems.setId(id);
                                stockItems.setCode(code);
                                stockItems.setName(name);
                                stockDetails.setPrice(price);
                                stockDetails.setPreviousPrice(price);
                                stockDetails.setVolume(volume);
                                stockDetails.setHitCount(hitCount);
                                stockItems.setStockDetails(stockDetails);
                                return stockItems;
                            }).collect(Collectors.toList());
            stockItemsRepository.saveAll(stockItemsList);
        };
    }

    @Bean
    public Reader csvFileReader() throws IOException {
        return new FileReader(new ClassPathResource("SampleData.csv").getFile());
    }

    @Bean
    public CSVParser csvParser() throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
        return new CSVParser(csvFileReader(), csvFormat);
    }
}
