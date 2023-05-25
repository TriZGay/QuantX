package io.futakotome.trade.domain;


import io.futakotome.trade.mapper.PlateDtoMapper;

@Deprecated(since = "获取股票/期货/期权接口换成从静态数据接口获取")
public interface RequestStockInfo {
    void sendStockInfoRequest(PlateDtoMapper plateDtoMapper);
}
