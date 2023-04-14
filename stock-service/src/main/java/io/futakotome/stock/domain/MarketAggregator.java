package io.futakotome.stock.domain;

import com.futu.openapi.pb.QotCommon;
import io.futakotome.stock.mapper.PlateDtoMapper;
import io.futakotome.stock.mapper.StockDtoMapper;

import java.util.Arrays;
import java.util.List;

public class MarketAggregator implements RequestPlateInfo, RequestStockInfo, RequestStaticInfo {
    private static final List<RequestPlateInfo> allMarketPlateInfo = Arrays.asList(
            new HKMarket(),
            new BigASHMarkert(),
            new BigASZMarket(),
            new USMarket()
//            new SGMarket(),
//            new JPMarket()
    );

    private static final List<RequestStockInfo> allMarketStockInfo = Arrays.asList(
            new HKMarket(),
            new BigASZMarket(),
            new BigASHMarkert(),
            new USMarket()
//            new SGMarket(),
//            new JPMarket()
    );

    private static final List<RequestStaticInfo> allMarketStaticInfo = Arrays.asList(
            new HKMarket(),
            new BigASHMarkert(),
            new BigASHMarkert(),
            new USMarket()
    );

    @Override
    public void sendPlateInfoRequest() {
        for (RequestPlateInfo perMarket : allMarketPlateInfo) {
            perMarket.sendPlateInfoRequest();
        }
    }

    @Override
    public void sendPlateInfoRequest(StockDtoMapper stockMapper) {
        for (RequestPlateInfo perMarket : allMarketPlateInfo) {
            perMarket.sendPlateInfoRequest(stockMapper);
        }
    }

    @Override
    public void sendStockInfoRequest(PlateDtoMapper plateDtoMapper) {
        for (RequestStockInfo perMarket : allMarketStockInfo) {
            perMarket.sendStockInfoRequest(plateDtoMapper);
        }
    }

    @Override
    public void sendStaticInfoRequest() {
        for (RequestStaticInfo perMarket : allMarketStaticInfo) {
            perMarket.sendStaticInfoRequest();
        }
    }
}
