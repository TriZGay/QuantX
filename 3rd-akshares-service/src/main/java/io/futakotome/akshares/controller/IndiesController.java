package io.futakotome.akshares.controller;

import io.futakotome.akshares.client.AkSharesHttpClient;
import io.futakotome.akshares.controller.vo.StockZhIndexResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indies")
public class IndiesController {
    private final AkSharesHttpClient akSharesHttpClient;

    public IndiesController(AkSharesHttpClient akSharesHttpClient) {
        this.akSharesHttpClient = akSharesHttpClient;
    }

    @GetMapping("/bigA-stock-indies/{symbol}")
    public ResponseEntity<?> fetchZhStockIndies(@PathVariable String symbol) {
        StockZhIndexResponse resp = new StockZhIndexResponse();
        resp.setStockZhIndexList(akSharesHttpClient.fetchStockZhIndies(symbol));
        return ResponseEntity.ok(resp);
    }
}
