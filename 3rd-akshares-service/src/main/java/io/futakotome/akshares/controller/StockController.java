package io.futakotome.akshares.controller;

import io.futakotome.akshares.client.AkSharesHttpClient;
import io.futakotome.akshares.controller.vo.BigAStockIndividual;
import io.futakotome.akshares.controller.vo.SHSZTodaySummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
    private final AkSharesHttpClient akSharesHttpClient;

    public StockController(AkSharesHttpClient akSharesHttpClient) {
        this.akSharesHttpClient = akSharesHttpClient;
    }

    @GetMapping("/today-summary")
    public ResponseEntity<?> fetchStocks() {
        SHSZTodaySummary summary = new SHSZTodaySummary();
        summary.setShStockSummaries(akSharesHttpClient.fetchSHStockSummaries());
        summary.setSzSummaries(akSharesHttpClient.fetchSZSummaries());
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/bigA-individual/{code}")
    public ResponseEntity<?> fetchIndividual(@PathVariable String code) {
        BigAStockIndividual resp = new BigAStockIndividual();
        resp.setStockItems(akSharesHttpClient.fetchBigAStockIndividual(code));
        return ResponseEntity.ok(resp);
    }
}
