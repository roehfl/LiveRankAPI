package com.kakaopaysec.liverankapi.domain.dto;

import lombok.Builder;

@Builder
public class StockInfoDTO {
    private String code;
    private String name;
    private int volume;
    private int price;
    private int priceDiff;
    private int hitCount;
}
