package com.apifinanceiro.apifeign.mapper;

import org.springframework.data.util.Pair;

import com.apifinanceiro.apifeign.controller.request.StockAddPurchaseRequest;
import com.apifinanceiro.apifeign.controller.request.StockRequest;
import com.apifinanceiro.apifeign.entity.Stock;
import com.apifinanceiro.apifeign.entity.StockPurchase;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StockMapper {

    public Pair<Stock, StockPurchase> toStock(StockRequest request) {
        Stock stock = createStockFromRequest(request);
        StockPurchase stockPurchase = createStockPurchaseFromRequest(request);
        return Pair.of(stock, stockPurchase);
    }

    public StockPurchase toStockPurchase(StockAddPurchaseRequest request) {
        return createStockPurchaseFromRequest(request);
    }

    private Stock createStockFromRequest(StockRequest request) {
        return Stock.builder()
                .stock(request.getStock())
                .build();
    }

    private StockPurchase createStockPurchaseFromRequest(StockRequest request) {
        return StockPurchase.builder()
                .date(request.getDate())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();
    }

    private StockPurchase createStockPurchaseFromRequest(StockAddPurchaseRequest request) {
        return StockPurchase.builder()
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .date(request.getDate())
                .build();
    }
}
