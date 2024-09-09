package com.apifinanceiro.apifeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.apifinanceiro.apifeign.controller.request.StockAddPurchaseRequest;
import com.apifinanceiro.apifeign.entity.StockPurchase;

@SpringBootApplication
public class ApifeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApifeignApplication.class, args);
    }

    public static StockPurchase toStockPurchase(StockAddPurchaseRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'toStockPurchase'");
    }

}
