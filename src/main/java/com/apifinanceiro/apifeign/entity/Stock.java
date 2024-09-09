package com.apifinanceiro.apifeign.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stock")
public class Stock {

    @Id
    private String id;

    private String stock;

    @Transient
    private BigDecimal price;

    @DBRef
    private List<StockPurchase> purchases;

    // private User user;

}
