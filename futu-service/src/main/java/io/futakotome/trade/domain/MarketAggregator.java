package io.futakotome.trade.domain;

import io.futakotome.trade.mapper.PlateDtoMapper;
import io.futakotome.trade.mapper.StockDtoMapper;

import java.util.Arrays;
import java.util.List;

public class MarketAggregator implements
        RequestPlateInfo, RequestStockInfo, RequestStaticInfo, RequestTradeDate {

    private static final List<RequestPlateInfo> allMarketPlateInfo = Arrays.asList(
            new HKMarket(),
            new BigASHMarket(),
            new BigASZMarket()
//            new SGMarket(),
//            new JPMarket()
    );

    private static final List<RequestStockInfo> allMarketStockInfo = Arrays.asList(
            new HKMarket(),
            new BigASZMarket(),
            new BigASHMarket()
//            new SGMarket(),
//            new JPMarket()
    );

    private static final List<RequestStaticInfo> allMarketStaticInfo = Arrays.asList(
            new HKMarket(),
            new BigASHMarket(),
            new BigASZMarket()
    );

    private static final List<RequestStaticInfo> allMarketIpoInfo = Arrays.asList(
            new HKMarket(),
            new BigASHMarket(),
            new BigASZMarket()
    );

    private static final List<RequestTradeDate> allMarketTradeDate = Arrays.asList(
            new HKMarket(),
//            new BigASZMarket(), 统一传 3:A股
            new BigASHMarket()
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

    @Override
    public void sendIpoInfoRequest() {
        for (RequestStaticInfo perMarket : allMarketIpoInfo) {
            perMarket.sendIpoInfoRequest();
        }
    }

    @Override
    public void sendTradeDateRequest() {
        for (RequestTradeDate perMarket : allMarketTradeDate) {
            perMarket.sendTradeDateRequest();
        }
    }
}
