package com.kakaopaysec.liverankapi.validator;

import com.kakaopaysec.liverankapi.dto.StockRankParamsDTO;

public class StockRankParamsValidator {
    public static void validate(StockRankParamsDTO params) {
        if (!((params.getTag() >= 1 && params.getTag() <= 4))) {
            throw new IllegalArgumentException("Tag must be between 1 and 4");
        }
        if (params.getPageNumber() < 0) {
            throw new IllegalArgumentException("pageNumber must be greater than zero");
        }
        if(params.getPageSize() <= 0) {
            throw new IllegalArgumentException("pageSize must be greater than zero");
        }
    }
}
