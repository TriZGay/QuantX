package io.futakotome.stock.domain;

import io.futakotome.stock.mapper.PlateDtoMapper;

@Deprecated
public interface RequestStockInfo {
    void sendStockInfoRequest(PlateDtoMapper plateDtoMapper);
}
