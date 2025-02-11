package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.controller.vo.ListStockRequest;
import io.futakotome.trade.controller.vo.ListStockResponse;
import io.futakotome.trade.dto.StockDto;

public interface StockDtoService extends IService<StockDto> {
    IPage<ListStockResponse> page(ListStockRequest listStockRequest);
}
