package com.kakaopaysec.liverankapi.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StockDetail {
    @Id
    private Long id;
    private int price;
    private int previousPrice;
    private int priceDiff;
    private double priceDiffPercentage;
    private int hitCount;
    private int volume;
    private Long itemId;
}
