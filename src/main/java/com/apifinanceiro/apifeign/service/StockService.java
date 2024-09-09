package com.apifinanceiro.apifeign.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apifinanceiro.apifeign.client.response.BrapiStockDataResponse;
import com.apifinanceiro.apifeign.entity.Stock;
import com.apifinanceiro.apifeign.entity.StockPurchase;
import com.apifinanceiro.apifeign.repository.StockPurchaseRepository;
import com.apifinanceiro.apifeign.repository.StockRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockPurchaseRepository stockPurchaseRepository;
    private final FindStockDetailService findStockDetailService;

    // Salva uma nova compra de ação e associa à entidade Stock
    public Stock savePurchase(Stock stock, StockPurchase stockPurchase) {
        StockPurchase savedStockPurchase = stockPurchaseRepository.save(stockPurchase);

        // Adiciona a compra à lista de compras da ação (inicializando a lista se for
        // nula)
        if (stock.getPurchases() == null) {
            stock.setPurchases(List.of(savedStockPurchase));
        } else {
            stock.getPurchases().add(savedStockPurchase);
        }

        // Salva e retorna a entidade Stock atualizada
        return stockRepository.save(stock);
    }

    // Adiciona uma nova compra de ação a um Stock existente
    public Stock addPurchase(String stockId, StockPurchase stockPurchase) {
        // Busca a entidade Stock por ID, ou lança exceção se não for encontrada
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found."));

        // Salva a compra e a associa à entidade Stock
        StockPurchase savedStockPurchase = stockPurchaseRepository.save(stockPurchase);
        stock.getPurchases().add(savedStockPurchase);

        // Salva e retorna a entidade Stock atualizada
        return stockRepository.save(stock);
    }

    // Retorna todas as ações e atualiza o preço de mercado com os dados da API
    // Brapi
    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll(); // Busca todas as ações

        // Para cada ação, atualiza o preço de mercado usando o serviço
        // FindStockDetailService
        stocks.forEach(this::updateStockPrice);

        return stocks; // Retorna a lista de ações atualizada
    }

    private void updateStockPrice(Stock stock) {
        // Obtém os detalhes do estoque usando o serviço FindStockDetailService
        findStockDetailService.getBrapiStockDetail(stock.getStock())
                // Mapeia para o preço de mercado regular
                .map(BrapiStockDataResponse::getRegularMarketPrice)
                // Converte o preço para BigDecimal
                .map(BigDecimal::valueOf)
                // Se o preço estiver presente, atualiza o preço da ação
                .ifPresent(stock::setPrice);

        // Salva a atualização do preço no banco de dados
        stockRepository.save(stock);
    }
}
