package com.apifinanceiro.apifeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.apifinanceiro.apifeign.client.response.BrapiStockResponse;

@FeignClient(name = "BrapiClient", url = "${stock.client.brapi.url}")
public interface BrapiClient {

    @GetMapping("/{stock}?token={token}")
    BrapiStockResponse getStock(@PathVariable("stock") String stock, @PathVariable("token") String token);
}
