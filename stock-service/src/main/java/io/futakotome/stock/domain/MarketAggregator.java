package io.futakotome.stock.domain;

import io.futakotome.stock.mapper.PlateDtoMapper;

import java.util.Arrays;
import java.util.List;

public class MarketAggregator implements RequestPlateInfo, RequestStockInfo {
    private static final List<RequestPlateInfo> allMarketPlateInfo = Arrays.asList(
            new HKMarket(),
            new BigASHMarkert(),
            new BigASZMarket(),
            new USMarket(),
            new SGMarket(),
            new JPMarket()
    );

    private static final List<RequestStockInfo> allMarketStockInfo = Arrays.asList(
            new HKMarket()
    );

    @Override
    public void sendPlateInfoRequest() {
        for (RequestPlateInfo perMarket : allMarketPlateInfo) {
            perMarket.sendPlateInfoRequest();
        }
    }

    @Override
    public void sendStockInfoRequest(PlateDtoMapper plateDtoMapper) {
        for (RequestStockInfo perMarket : allMarketStockInfo) {
            perMarket.sendStockInfoRequest(plateDtoMapper);
        }
    }
}
