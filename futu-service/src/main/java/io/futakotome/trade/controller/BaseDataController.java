package io.futakotome.trade.controller;

import io.futakotome.trade.controller.vo.*;
import io.futakotome.trade.domain.SnapshotService;
import io.futakotome.trade.domain.code.*;
import io.futakotome.trade.service.PlateDtoService;
import io.futakotome.trade.service.StockDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/base")
public class BaseDataController {
    private final StockDtoService stockDtoService;
    private final PlateDtoService plateDtoService;
    private final SnapshotService snapshotService;

    public BaseDataController(StockDtoService stockDtoService, PlateDtoService plateDtoService, SnapshotService snapshotService) {
        this.stockDtoService = stockDtoService;
        this.plateDtoService = plateDtoService;
        this.snapshotService = snapshotService;
    }

    @PostMapping("/stocks")
    public ResponseEntity<?> listStocks(@RequestBody ListStockRequest request) {
        return ResponseEntity.ok(stockDtoService.page(request));
    }

    @PostMapping("/allStocks")
    public ResponseEntity<?> allStocks(@RequestBody ListStockRequest request) {
        return ResponseEntity.ok(stockDtoService.fetchAll(request));
    }

    @GetMapping("/getStock/{code}")
    public ResponseEntity<?> getStockByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(stockDtoService.fetchByCode(code));
    }

    @PostMapping("/plates")
    public ResponseEntity<?> listPlates(@RequestBody ListPlateRequest request) {
        return ResponseEntity.ok(plateDtoService.page(request));
    }

    @PostMapping("/snapshots")
    public ResponseEntity<?> securitySnapshot(@RequestBody CommonSecurityRequest securityRequest) {
        return ResponseEntity.ok(snapshotService.querySnapshot(securityRequest));
    }

    @GetMapping("/stockFiltersMeta")
    public ResponseEntity<?> stockFiltersMetaCodes() {
        StockFilterMetaResponse stockFilterMetaResponse = new StockFilterMetaResponse();
        stockFilterMetaResponse.setSortDirs(toSelectOptions(SortDir.values()));
        stockFilterMetaResponse.setKlTypes(toSelectOptions(KLType.values()));
        stockFilterMetaResponse.setRelativePositions(toSelectOptions(RelativePosition.values()));
        stockFilterMetaResponse.setBaseFiltersFields(toSelectOptions(StockBaseField.values()));
        stockFilterMetaResponse.setFinancialFiltersFields(toSelectOptions(StockFinancialField.values()));
        stockFilterMetaResponse.setFinancialQuarters(toSelectOptions(StockFinancialQuarter.values()));
        stockFilterMetaResponse.setAccumulateFiltersFields(toSelectOptions(StockAccumulateField.values()));
        stockFilterMetaResponse.setIndicatorFiltersFields(toSelectOptions(StockIndicatorField.values()));
        stockFilterMetaResponse.setPatternFiltersFields(toSelectOptions(StockPatternField.values()));
        return ResponseEntity.ok(stockFilterMetaResponse);
    }

    @GetMapping("/tradeMeta")
    public ResponseEntity<?> tradeMetaCodes() {
        TradeMetaResponse tradeMetaResponse = new TradeMetaResponse();
        tradeMetaResponse.setTradeSides(toSelectOptions(TradeSide.values()));
        tradeMetaResponse.setTrailTypes(toSelectOptions(TrailType.values()));
        tradeMetaResponse.setTimeInForces(toSelectOptions(TimeInForce.values()));
        tradeMetaResponse.setTradeSecMarkets(toSelectOptions(TradeSecurityMarket.values()));
        tradeMetaResponse.setOrderTypes(toSelectOptions(OrderType.values()));
        tradeMetaResponse.setMarketTypes(toSelectOptions(MarketType.values()));
        return ResponseEntity.ok(tradeMetaResponse);
    }

    private List<AntDesignSelectOptions> toSelectOptions(MarketType[] values) {
        return Arrays.stream(values).map(t -> new AntDesignSelectOptions(t.getName(), t.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(OrderType[] values) {
        return Arrays.stream(values).map(t -> new AntDesignSelectOptions(t.getName(), t.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(TradeSecurityMarket[] values) {
        return Arrays.stream(values).map(t -> new AntDesignSelectOptions(t.getName(), t.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(TimeInForce[] values) {
        return Arrays.stream(values).map(t -> new AntDesignSelectOptions(t.getName(), t.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(TrailType[] values) {
        return Arrays.stream(values).map(trailType -> new AntDesignSelectOptions(trailType.getName(), trailType.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(TradeSide[] values) {
        return Arrays.stream(values).map(tradeSide -> new AntDesignSelectOptions(tradeSide.getName(), tradeSide.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(StockPatternField[] values) {
        return Arrays.stream(values).map(stockPatternField -> new AntDesignSelectOptions(stockPatternField.getDesc(), stockPatternField.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(StockAccumulateField[] values) {
        return Arrays.stream(values).map(stockAccumulateField -> new AntDesignSelectOptions(stockAccumulateField.getDesc(), stockAccumulateField.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(StockFinancialQuarter[] values) {
        return Arrays.stream(values).map(stockFinancialQuarter -> new AntDesignSelectOptions(stockFinancialQuarter.getDesc(), stockFinancialQuarter.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(StockIndicatorField[] values) {
        return null;
    }

    private List<AntDesignSelectOptions> toSelectOptions(StockFinancialField[] values) {
        return Arrays.stream(values).map(stockFinancialField -> new AntDesignSelectOptions(stockFinancialField.getDesc(), stockFinancialField.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(StockBaseField[] values) {
        return Arrays.stream(values).map(stockBaseField -> new AntDesignSelectOptions(stockBaseField.getDesc(), stockBaseField.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(RelativePosition[] values) {
        return Arrays.stream(values).map(relativePosition -> new AntDesignSelectOptions(relativePosition.getDesc(), relativePosition.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(SortDir[] sortDirs) {
        return Arrays.stream(sortDirs).map(sortDir -> new AntDesignSelectOptions(sortDir.getDesc(), sortDir.getCode()))
                .collect(Collectors.toList());
    }

    private List<AntDesignSelectOptions> toSelectOptions(KLType[] klTypes) {
        return Arrays.stream(klTypes).map(klType -> new AntDesignSelectOptions(klType.getName(), klType.getCode()))
                .collect(Collectors.toList());
    }
}
