package io.futakotome.stock.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.futakotome.stock.dto.StockDto;
import io.futakotome.stock.mapper.StockDtoMapper;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/base")
public class BaseDataController {
    private final StockDtoMapper stockDtoMapper;

    public BaseDataController(StockDtoMapper stockDtoMapper) {
        this.stockDtoMapper = stockDtoMapper;
    }

    @PostMapping("/stocks")
    public Mono<IPage<StockDto>> listStocks(@RequestBody StockListRequest request,
                                            @RequestParam(required = false) Long page,
                                            @RequestParam(required = false) Long limit
    ) {
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
        return Mono.create(iPageFluxSink ->
                iPageFluxSink.success(stockDtoMapper.selectPage(pagination, queryWrapper))
        );
    }
}
