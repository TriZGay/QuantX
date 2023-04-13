package io.futakotome.stock.domain;

import io.futakotome.stock.mapper.StockDtoMapper;

public interface RequestPlateInfo {
    @Deprecated(since = "板块信息获取换成从股票列表获取")
    void sendPlateInfoRequest();

    void sendPlateInfoRequest(StockDtoMapper stockMapper);
}
