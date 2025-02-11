package io.futakotome.trade.controller;

import io.futakotome.trade.controller.vo.ListStockRequest;
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

    public BaseDataController(StockDtoService stockDtoService) {
        this.stockDtoService = stockDtoService;
    }

    @PostMapping("/stocks")
    public ResponseEntity<?> listStocks(@RequestBody ListStockRequest request) {
        return ResponseEntity.ok(stockDtoService.page(request));
    }

//
//    @PostMapping("/plates")
//
//    public Mono<IPage<PlateDto>> listPlates(@RequestBody PlateListRequest plateListRequest,
//                                            @RequestParam(required = false) Long page,
//                                            @RequestParam(required = false) Long limit) {
//        QueryWrapper<PlateDto> queryWrapper = Wrappers.query();
//        Page<PlateDto> pagination = Page.of(1, 10);
//        if (plateListRequest.getMarket() != null) {
//            queryWrapper.eq("market", plateListRequest.getMarket());
//        }
//        if (page != null) {
//            pagination.setCurrent(page);
//        }
//        if (limit != null) {
//            pagination.setSize(limit);
//        }
//        return Mono.create(iPageMonoSink ->
//                iPageMonoSink.success(plateDtoMapper.selectPage(pagination, queryWrapper)));
//    }

}
