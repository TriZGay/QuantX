package io.futakotome.stock.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.futakotome.stock.dto.IpoHkDto;
import io.futakotome.stock.dto.IpoUsDto;
import io.futakotome.stock.dto.PlateDto;
import io.futakotome.stock.dto.StockDto;
import io.futakotome.stock.mapper.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/base")
public class BaseDataController {
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

    @PostMapping("/ipos")
    public Mono<String> listIpos() {
        QueryWrapper<IpoHkDto> ipoHkDtoQueryWrapper = Wrappers.query();
        List<IpoHkDto> hkIpos = ipoHkDtoMapper.selectList(ipoHkDtoQueryWrapper);
        QueryWrapper<IpoUsDto> ipoUsDtoQueryWrapper = Wrappers.query();
        List<IpoUsDto> usIpos = ipoUsDtoMapper.selectList(ipoUsDtoQueryWrapper);
        return Mono.create(stringMonoSink -> {
            JsonObject ipos = new JsonObject();
            ipos.addProperty("hk", GSON.toJson(hkIpos));
            ipos.addProperty("us",GSON.toJson(usIpos));
            stringMonoSink.success(
                    GSON.toJson(ipos)
            );
        });
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
