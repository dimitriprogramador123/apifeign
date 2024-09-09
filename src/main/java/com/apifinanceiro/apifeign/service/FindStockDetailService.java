package com.apifinanceiro.apifeign.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apifinanceiro.apifeign.client.BrapiClient;
import com.apifinanceiro.apifeign.client.response.BrapiStockDataResponse;
import com.apifinanceiro.apifeign.client.response.BrapiStockResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class FindStockDetailService {

    private static final Logger logger = LoggerFactory.getLogger(FindStockDetailService.class);

    @Value("${stock.client.brapi.token}")
    private String token;

    private final BrapiClient brapiClient;

    public FindStockDetailService(BrapiClient brapiClient, @Value("${stock.client.brapi.token}") String token) {
        this.brapiClient = brapiClient;
        this.token = token;
    }

    public Optional<BrapiStockDataResponse> getBrapiStockDetail(String stock) {
        logger.info("Consultando informações da ação: {} na Brapi", stock);

        // Usamos Optional para encapsular a resposta da chamada ao Brapi
        return Optional.ofNullable(stock)
                .map(this::extracted);
    }

    private BrapiStockDataResponse extracted(String symbol) {
        try {
            // Chamada ao cliente Brapi
            final BrapiStockResponse brapiStockResponse = brapiClient.getStock(symbol, token);

            // Usando Optional para lidar com os resultados da resposta
            return Optional.ofNullable(brapiStockResponse)
                    .map(BrapiStockResponse::getResults)
                    .filter(results -> !results.isEmpty())
                    .map(results -> {
                        BrapiStockDataResponse stockData = results.get(0);
                        logger.info("Retorno Brapi da ação {}: {}", symbol, stockData);
                        return stockData;
                    })
                    .orElseGet(() -> {
                        logger.warn("Nenhum resultado encontrado para a ação: {}", symbol);
                        return null;
                    });
        } catch (Exception e) {
            logger.error("Erro ao consultar informações da ação {} na Brapi: {}", symbol, e.getMessage());
            return null;
        }
    }
}
