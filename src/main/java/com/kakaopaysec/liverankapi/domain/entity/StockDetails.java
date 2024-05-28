package com.kakaopaysec.liverankapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StockDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int price;
    private int previousPrice;
    private int hitCount;
    private int volume;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "stockItems_id")
    private StockItems stockItems;
}
