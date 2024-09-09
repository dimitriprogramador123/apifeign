package com.apifinanceiro.apifeign.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockAddPurchaseRequest {

    private String stockId;
    private long quantity;
    private LocalDate date;
    private BigDecimal price;
}
