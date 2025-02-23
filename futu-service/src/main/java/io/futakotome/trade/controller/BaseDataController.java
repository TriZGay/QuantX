package io.futakotome.trade.controller;

import io.futakotome.trade.controller.vo.CommonSecurityRequest;
import io.futakotome.trade.controller.vo.ListPlateRequest;
import io.futakotome.trade.controller.vo.ListStockRequest;
import io.futakotome.trade.domain.SnapshotService;
import io.futakotome.trade.service.PlateDtoService;
import io.futakotome.trade.service.StockDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
