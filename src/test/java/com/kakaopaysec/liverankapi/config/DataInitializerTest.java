package com.kakaopaysec.liverankapi.config;

import com.kakaopaysec.liverankapi.common.CommonUtils;
import com.kakaopaysec.liverankapi.domain.repository.StockItemsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class DataInitializerTest {
    StockItemsRepository stockItemsRepository;
    CommonUtils commonUtils;
    DataInitializer dataInitializer;

    @BeforeEach
    public void setUp() {
        stockItemsRepository = mock(StockItemsRepository.class);
        mock(CommonUtils.class);
        new DataInitializer(stockItemsRepository, commonUtils);
    }

    @Test
    void dataInitializerConstructorTest() {
        assertNotNull(dataInitializer, "DataInitializer should not be null");
    }
}