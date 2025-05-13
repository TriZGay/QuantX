package io.futakotome.trade.controller;

import io.futakotome.trade.controller.vo.CommonSecurityRequest;
import io.futakotome.trade.controller.vo.ListPlateRequest;
import io.futakotome.trade.controller.vo.ListStockRequest;
import io.futakotome.trade.controller.vo.StockFilterMetaResponse;
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

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(StockPatternField[] values) {
        return Arrays.stream(values).map(stockPatternField -> new StockFilterMetaResponse.AntDesignSelectOptions(stockPatternField.getDesc(), stockPatternField.getCode()))
                .collect(Collectors.toList());
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(StockAccumulateField[] values) {
        return Arrays.stream(values).map(stockAccumulateField -> new StockFilterMetaResponse.AntDesignSelectOptions(stockAccumulateField.getDesc(), stockAccumulateField.getCode()))
                .collect(Collectors.toList());
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(StockFinancialQuarter[] values) {
        return Arrays.stream(values).map(stockFinancialQuarter -> new StockFilterMetaResponse.AntDesignSelectOptions(stockFinancialQuarter.getDesc(), stockFinancialQuarter.getCode()))
                .collect(Collectors.toList());
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(StockIndicatorField[] values) {
        return null;
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(StockFinancialField[] values) {
        return Arrays.stream(values).map(stockFinancialField -> new StockFilterMetaResponse.AntDesignSelectOptions(stockFinancialField.getDesc(), stockFinancialField.getCode()))
                .collect(Collectors.toList());
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(StockBaseField[] values) {
        return Arrays.stream(values).map(stockBaseField -> new StockFilterMetaResponse.AntDesignSelectOptions(stockBaseField.getDesc(), stockBaseField.getCode()))
                .collect(Collectors.toList());
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(RelativePosition[] values) {
        return Arrays.stream(values).map(relativePosition -> new StockFilterMetaResponse.AntDesignSelectOptions(relativePosition.getDesc(), relativePosition.getCode()))
                .collect(Collectors.toList());
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(SortDir[] sortDirs) {
        return Arrays.stream(sortDirs).map(sortDir -> new StockFilterMetaResponse.AntDesignSelectOptions(sortDir.getDesc(), sortDir.getCode()))
                .collect(Collectors.toList());
    }

    private List<StockFilterMetaResponse.AntDesignSelectOptions> toSelectOptions(KLType[] klTypes) {
        return Arrays.stream(klTypes).map(klType -> new StockFilterMetaResponse.AntDesignSelectOptions(klType.getName(), klType.getCode()))
                .collect(Collectors.toList());
    }
}
