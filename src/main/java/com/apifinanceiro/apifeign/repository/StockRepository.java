package com.apifinanceiro.apifeign.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.apifinanceiro.apifeign.entity.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, String> {
    // Exemplo de método de consulta personalizada
    List<Stock> findBySymbol(String symbol);
}
