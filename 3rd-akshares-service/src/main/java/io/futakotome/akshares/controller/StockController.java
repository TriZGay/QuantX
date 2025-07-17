package io.futakotome.akshares.controller;

import io.futakotome.akshares.client.AkSharesHttpClient;
import io.futakotome.akshares.controller.vo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        StockZhTodaySummary summary = new StockZhTodaySummary();
        summary.setShStockSummaries(StockZhTodaySummary.StockShSummaryVo.dtoListToVoList(akSharesHttpClient.fetchSHStockSummaries()));
        summary.setSzSummaries(StockZhTodaySummary.StockSzSummaryVo.dtoListToVoList(akSharesHttpClient.fetchSZSummaries()));
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
        response.setBidAskItems(akSharesHttpClient.fetchStockBidAsk(symbol));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bigA-history")
    public ResponseEntity<?> fetchBigAHistoryPrice(@RequestBody StockZhHistoryRequest request) {
        StockZhHistoryResponse response = new StockZhHistoryResponse();
        response.setHistories(StockZhHistoryResponse.StockZhHistoryVo.dtoListToVoList(akSharesHttpClient.fetchStockZhHistory(request)));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bigA-rt/{type}")
    public ResponseEntity<?> fetchBigARTPrice(@PathVariable String type) {
        StockZhRtResponse resp = new StockZhRtResponse();
        if (ALL_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesHttpClient.fetchAllZhStockRtPrice()));
        } else if (SH_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesHttpClient.fetchShStockRtPrice()));
        } else if (SZ_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesHttpClient.fetchSzStockRtPrice()));
        } else if (BJ_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesHttpClient.fetchBjStockRtPrice()));
        }
        return ResponseEntity.ok(resp);
    }
}
