package com.kakaopaysec.liverankapi.config;

import com.kakaopaysec.liverankapi.common.CommonUtils;
import com.kakaopaysec.liverankapi.domain.repository.StockItemsRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DataInitializerTest {
    @Test
    void dataInitializerConstructorTest() {
        StockItemsRepository stockItemsRepository = mock(StockItemsRepository.class);
        CommonUtils commonUtils = mock(CommonUtils.class);

        DataInitializer dataInitializer = new DataInitializer(stockItemsRepository, commonUtils);

        assertNotNull(dataInitializer, "DataInitializer should not be null");
    }

    @Test
    void loadDataTest() {
        // 이건 내일 무슨의민지 확인좀 해보자..
        StockItemsRepository stockItemsRepository = mock(StockItemsRepository.class);
        CommonUtils commonUtils = mock(CommonUtils.class);

        DataInitializer dataInitializer = new DataInitializer(stockItemsRepository, commonUtils);

        // Call the method under test
        dataInitializer.loadData();
        // Verify that the method call was as expected
        verify(stockItemsRepository).saveAll(anyList());
    }
}