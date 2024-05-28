package com.kakaopaysec.liverankapi.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class StockItems {
    @Id
    private Long id;
    private String code;
    private String name;

    @OneToOne(mappedBy = "stockItems", cascade = CascadeType.ALL)
    private StockDetails stockDetails;
}
