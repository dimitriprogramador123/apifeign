package com.apifinanceiro.apifeign.controller;

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
        return Optional.ofNullable(stock)
                .map(this::fetchStockData);
    }

    private BrapiStockDataResponse fetchStockData(String symbol) {
        try {
            BrapiStockResponse response = getBrapiStockResponse(symbol);
            return processStockResponse(symbol, response);
        } catch (Exception e) {
            logger.error("Erro ao consultar informações da ação {} na Brapi: {}", symbol, e.getMessage());
            return null;
        }
    }

    private BrapiStockResponse getBrapiStockResponse(String symbol) {
        return brapiClient.getStock(symbol, token);
    }

    private BrapiStockDataResponse processStockResponse(String symbol, BrapiStockResponse response) {
        return Optional.ofNullable(response)
                .map(BrapiStockResponse::getResults)
                .filter(results -> !results.isEmpty())
                .map(results -> results.get(0))
                .map(stockData -> {
                    logger.info("Retorno Brapi da ação {}: {}", symbol, stockData);
                    return stockData;
                })
                .orElseGet(() -> {
                    logger.warn("Nenhum resultado encontrado para a ação: {}", symbol);
                    return null;
                });
    }
}
