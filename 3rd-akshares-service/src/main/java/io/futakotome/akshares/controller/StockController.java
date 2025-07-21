package io.futakotome.akshares.controller;

import io.futakotome.akshares.controller.vo.*;
import io.futakotome.akshares.service.AkSharesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final AkSharesService akSharesService;
    private static final String ALL_TYPE = "all";
    private static final String SZ_TYPE = "sz";
    private static final String SH_TYPE = "sh";
    private static final String BJ_TYPE = "bj";

    public StockController(AkSharesService akSharesService) {
        this.akSharesService = akSharesService;
    }

    @GetMapping("/today-summary")
    public ResponseEntity<?> fetchStocks() {
        StockZhTodaySummary summary = new StockZhTodaySummary();
        summary.setShStockSummaries(StockZhTodaySummary.StockShSummaryVo.dtoListToVoList(akSharesService.fetchSHStockSummaries()));
        summary.setSzSummaries(StockZhTodaySummary.StockSzSummaryVo.dtoListToVoList(akSharesService.fetchSZSummaries()));
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/bigA-individual/{code}")
    public ResponseEntity<?> fetchIndividual(@PathVariable String code) {
        BigAStockIndividual resp = new BigAStockIndividual();
        resp.setStockItems(akSharesService.fetchBigAStockIndividual(code));
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/bigA-individual-info/{code}")
    public ResponseEntity<?> fetchIndividualInfo(@PathVariable String code) {
        BigAStockIndividual resp = new BigAStockIndividual();
        resp.setStockItems(akSharesService.fetchBigAStockIndividualInfo(code));
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/bigA-bidAsk/{symbol}")
    public ResponseEntity<?> fetchBidAsk(@PathVariable String symbol) {
        StockBidAskResponse response = new StockBidAskResponse();
        response.setBidAskItems(akSharesService.fetchStockBidAsk(symbol));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bigA-history")
    public ResponseEntity<?> fetchBigAHistoryPrice(@RequestBody StockZhHistoryRequest request) {
        StockZhHistoryResponse response = new StockZhHistoryResponse();
        response.setHistories(StockZhHistoryResponse.StockZhHistoryVo.dtoListToVoList(akSharesService.fetchStockZhHistory(request)));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bigA-rt/{type}")
    public ResponseEntity<?> fetchBigARTPrice(@PathVariable String type) {
        StockZhRtResponse resp = new StockZhRtResponse();
        if (ALL_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesService.fetchAllZhStockRtPrice()));
        } else if (SH_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesService.fetchShStockRtPrice()));
        } else if (SZ_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesService.fetchSzStockRtPrice()));
        } else if (BJ_TYPE.equals(type)) {
            resp.setRtPrices(StockZhRtResponse.StockRTPriceVo.dtoListToVoList(akSharesService.fetchBjStockRtPrice()));
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/us-rt")
    public ResponseEntity<?> fetchUsRTPrice() {
        StockUsRtResponse response = new StockUsRtResponse();
        response.setStockUsRtPrices(StockUsRtResponse.StockUsRtPriceVo.dtoListToVoList(akSharesService.fetchStockUsRealTime()));
        return ResponseEntity.ok(response);
    }
}
