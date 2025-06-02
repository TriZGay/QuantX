package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.controller.vo.ListStockRequest;
import io.futakotome.trade.controller.vo.ListStockResponse;
import io.futakotome.trade.dto.StockDto;

import java.util.List;

public interface StockDtoService extends IService<StockDto> {
    int insertBatch(List<StockDto> list);

    IPage<ListStockResponse> page(ListStockRequest listStockRequest);

    List<ListStockResponse> fetchAll(ListStockRequest listStockRequest);

    ListStockResponse fetchByCode(String code);
}
