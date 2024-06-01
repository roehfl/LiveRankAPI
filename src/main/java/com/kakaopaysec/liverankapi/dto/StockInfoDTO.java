package com.kakaopaysec.liverankapi.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StockInfoDTO {
    private String code;
    private String name;
    private int volume;
    private int price;
    private int priceDiff;
    private double priceDiffPercent;
    private int hitCount;
}
