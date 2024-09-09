package com.apifinanceiro.apifeign.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.apifinanceiro.apifeign.entity.Stock;

@Repository
public interface StockPurchaseRepository extends MongoRepository<StockPurchase, String> {

    // Método para salvar uma entidade Stock
    Stock save(Stock stock);

    // Método para salvar uma entidade StockPurchase
    com.apifinanceiro.apifeign.entity.StockPurchase save(
            com.apifinanceiro.apifeign.entity.StockPurchase stockPurchase);
}
