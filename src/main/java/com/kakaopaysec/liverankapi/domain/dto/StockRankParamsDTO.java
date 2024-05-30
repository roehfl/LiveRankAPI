package com.kakaopaysec.liverankapi.domain.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@Builder
public class StockRankParamsDTO {
    private int pageNumber;
    private int pageSize;
    private String tag;
    private Sort.Direction sortOrder;
}
