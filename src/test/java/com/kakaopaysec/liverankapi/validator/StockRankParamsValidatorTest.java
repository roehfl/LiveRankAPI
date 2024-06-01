package com.kakaopaysec.liverankapi.validator;

import com.kakaopaysec.liverankapi.dto.StockRankParamsDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StockRankParamsValidatorTest {
    @Test
    public void validateValidParams() {
        StockRankParamsDTO validParams = StockRankParamsDTO.builder()
                .tag(1)
                .pageNumber(0)
                .pageSize(30)
                .build();
        Assertions.assertDoesNotThrow(() -> StockRankParamsValidator.validate(validParams));
    }

    @Test
    public void validateInValidParams() {
        StockRankParamsDTO inValidParamsTag = StockRankParamsDTO.builder()
                .tag(0)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> StockRankParamsValidator.validate(inValidParamsTag));

        StockRankParamsDTO inValidParamsPageSize = StockRankParamsDTO.builder()
                .tag(1)
                .pageSize(0)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> StockRankParamsValidator.validate(inValidParamsPageSize));

        StockRankParamsDTO inValidParamsPageNumber = StockRankParamsDTO.builder()
                .tag(1)
                .pageSize(10)
                .pageNumber(-1)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> StockRankParamsValidator.validate(inValidParamsPageNumber));
    }
}
