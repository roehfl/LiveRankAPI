package com.kakaopaysec.liverankapi.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockItem {
    @Id
    private Long id;
    private String code;
    private String name;
}
