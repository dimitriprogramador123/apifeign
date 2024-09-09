package com.apifinanceiro.apifeign.client.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrapiStockResponse {

    private List<BrapiStockDataResponse> results;
}
