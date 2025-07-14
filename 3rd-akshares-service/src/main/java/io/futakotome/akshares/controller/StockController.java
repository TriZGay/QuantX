package io.futakotome.akshares.controller;

import io.futakotome.akshares.client.AkSharesHttpClient;
import io.futakotome.akshares.controller.vo.BigAStockIndividual;
import io.futakotome.akshares.controller.vo.SHSZTodaySummary;
import io.futakotome.akshares.controller.vo.StockBidAskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final AkSharesHttpClient akSharesHttpClient;
    private static final String ALL_TYPE = "all";
    private static final String SZ_TYPE = "sz";
    private static final String SH_TYPE = "sh";
    private static final String BJ_TYPE = "bj";

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

    @GetMapping("/bigA-individual-info/{code}")
    public ResponseEntity<?> fetchIndividualInfo(@PathVariable String code) {
        BigAStockIndividual resp = new BigAStockIndividual();
        resp.setStockItems(akSharesHttpClient.fetchBigAStockIndividualInfo(code));
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/bigA-bidAsk/{symbol}")
    public ResponseEntity<?> fetchBidAsk(@PathVariable String symbol) {
        StockBidAskResponse response = new StockBidAskResponse();
        return ResponseEntity.ok(akSharesHttpClient.fetchStockBidAsk(symbol));
    }

    @GetMapping("/bigA-rt/{type}")
    public ResponseEntity<?> fetchBigARTPrice(@PathVariable String type) {
        if (ALL_TYPE.equals(type)) {
            return ResponseEntity.ok(akSharesHttpClient.fetchAllZhStockRtPrice());
        } else if (SH_TYPE.equals(type)) {
            return ResponseEntity.ok(akSharesHttpClient.fetchShStockRtPrice());
        } else if (SZ_TYPE.equals(type)) {
            return ResponseEntity.ok(akSharesHttpClient.fetchSzStockRtPrice());
        } else if (BJ_TYPE.equals(type)) {
            return ResponseEntity.ok(akSharesHttpClient.fetchBjStockRtPrice());
        } else {
            return ResponseEntity.ok("类型不能空");
        }
    }
}
