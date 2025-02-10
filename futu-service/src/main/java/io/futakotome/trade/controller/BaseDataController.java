package io.futakotome.trade.controller;

import com.google.gson.Gson;
import io.futakotome.trade.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class BaseDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDataController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "{}请求参数:{},分页参数:current[{}],size[{}]";
    private static final Gson GSON = new Gson();

    private final StockDtoMapper stockDtoMapper;
    private final PlateDtoMapper plateDtoMapper;
    private final IpoHkDtoMapper ipoHkDtoMapper;
    private final IpoUsDtoMapper ipoUsDtoMapper;
    private final IpoCnDtoMapper ipoCnDtoMapper;

    public BaseDataController(StockDtoMapper stockDtoMapper, IpoCnDtoMapper ipoCnDtoMapper, IpoUsDtoMapper ipoUsDtoMapper, IpoHkDtoMapper ipoHkDtoMapper, PlateDtoMapper plateDtoMapper) {
        this.stockDtoMapper = stockDtoMapper;
        this.plateDtoMapper = plateDtoMapper;
        this.ipoHkDtoMapper = ipoHkDtoMapper;
        this.ipoUsDtoMapper = ipoUsDtoMapper;
        this.ipoCnDtoMapper = ipoCnDtoMapper;
    }
//
//    @GetMapping("/plate/{stockId}")
//    public Mono<ResponseEntity<List<PlateDto>>> fetchPlateByStockId(@PathVariable("stockId") Long stockId) {
//        LOGGER.info(PRINT_REQUEST_TEMPLATE, "根据股票ID查询板块", "{}", 1, Integer.MAX_VALUE);
//        return Mono.create(responseEntityMonoSink ->
//                responseEntityMonoSink.success(
//                        new ResponseEntity<>(plateDtoMapper.searchALLByStockId(stockId), HttpStatus.OK)
//                ));
//    }
//
//    @PostMapping("/stocks")
//    public Mono<IPage<StockDto>> listStocks(@RequestBody StockListRequest request) {
//        LOGGER.info(PRINT_REQUEST_TEMPLATE, "查询股票", request.toString(), request.getCurrent(), request.getSize());
//        QueryWrapper<StockDto> queryWrapper = Wrappers.query();
//        Page<StockDto> pagination = Page.of(1, 10);
//        if (request.getName() != null) {
//            queryWrapper.like("name", request.getName());
//        }
//        if (request.getStockType() != null) {
//            queryWrapper.eq("stock_type", request.getStockType());
//        }
//        if (request.getMarket() != null) {
//            queryWrapper.eq("market", request.getMarket());
//        }
//        if (request.getExchangeType() != null) {
//            queryWrapper.eq("exchange_type", request.getExchangeType());
//        }
//        if (request.getDelisting() != null) {
//            queryWrapper.eq("delisting", request.getDelisting());
//        }
//        if (request.getCurrent() != null) {
//            pagination.setCurrent(request.getCurrent());
//        }
//        if (request.getSize() != null) {
//            pagination.setSize(request.getSize());
//        }
//        return Mono.create(iPageMonoSink ->
//                iPageMonoSink.success(stockDtoMapper.selectPage(pagination, queryWrapper))
//        );
//    }

//    @PostMapping("/ipos")
//    public Mono<String> listIpos() {
//        QueryWrapper<IpoHkDto> ipoHkDtoQueryWrapper = Wrappers.query();
//        List<IpoHkDto> hkIpos = ipoHkDtoMapper.selectList(ipoHkDtoQueryWrapper);
//        QueryWrapper<IpoUsDto> ipoUsDtoQueryWrapper = Wrappers.query();
//        List<IpoUsDto> usIpos = ipoUsDtoMapper.selectList(ipoUsDtoQueryWrapper);
//        List<IpoCnDto> cnIpos = ipoCnDtoMapper.findAll();
//        return Mono.create(stringMonoSink -> {
//            JsonObject ipos = new JsonObject();
//            ipos.add("hk", GSON.toJsonTree(hkIpos).getAsJsonArray());
//            ipos.add("us", GSON.toJsonTree(usIpos).getAsJsonArray());
//            ipos.add("cn", GSON.toJsonTree(cnIpos).getAsJsonArray());
//            stringMonoSink.success(
//                    GSON.toJson(ipos)
//            );
//        });
//    }
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
