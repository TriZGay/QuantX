package io.futakotome.stock.domain;

import io.futakotome.stock.mapper.PlateDtoMapper;

public interface RequestStockInfo {
    void sendStockInfoRequest(PlateDtoMapper plateDtoMapper);
}
