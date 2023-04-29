package io.futakotome.stock.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.futakotome.stock.dto.PlateDto;
import io.futakotome.stock.dto.StockDto;
import io.futakotome.stock.mapper.PlateDtoMapper;
import io.futakotome.stock.mapper.StockDtoMapper;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/base")
public class BaseDataController {
    private final StockDtoMapper stockDtoMapper;
    private final PlateDtoMapper plateDtoMapper;

    public BaseDataController(StockDtoMapper stockDtoMapper, PlateDtoMapper plateDtoMapper) {
        this.stockDtoMapper = stockDtoMapper;
        this.plateDtoMapper = plateDtoMapper;
    }

    @PostMapping("/stocks")
    public Mono<IPage<StockDto>> listStocks(@RequestBody StockListRequest request,
                                            @RequestParam(required = false) Long page,
                                            @RequestParam(required = false) Long limit) {
        QueryWrapper<StockDto> queryWrapper = Wrappers.query();
        Page<StockDto> pagination = Page.of(1, 10);
        if (request.getMarket() != null) {
            queryWrapper.eq("market", request.getMarket());
        }
        if (page != null) {
            pagination.setCurrent(page);
        }
        if (limit != null) {
            pagination.setSize(limit);
        }
        return Mono.create(iPageMonoSink ->
                iPageMonoSink.success(stockDtoMapper.selectPage(pagination, queryWrapper))
        );
    }

    @PostMapping("/plates")
    public Mono<IPage<PlateDto>> listPlates(@RequestBody PlateListRequest plateListRequest,
                                            @RequestParam(required = false) Long page,
                                            @RequestParam(required = false) Long limit) {
        QueryWrapper<PlateDto> queryWrapper = Wrappers.query();
        Page<PlateDto> pagination = Page.of(1, 10);
        if (plateListRequest.getMarket() != null) {
            queryWrapper.eq("market", plateListRequest.getMarket());
        }
        if (page != null) {
            pagination.setCurrent(page);
        }
        if (limit != null) {
            pagination.setSize(limit);
        }
        return Mono.create(iPageMonoSink ->
                iPageMonoSink.success(plateDtoMapper.selectPage(pagination, queryWrapper)));
    }

}
