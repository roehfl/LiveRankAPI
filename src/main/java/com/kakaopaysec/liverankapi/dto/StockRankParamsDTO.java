package com.kakaopaysec.liverankapi.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockRankParamsDTO {
    private int nextOffset;
    private int pageSize;
    private int tag;
}
