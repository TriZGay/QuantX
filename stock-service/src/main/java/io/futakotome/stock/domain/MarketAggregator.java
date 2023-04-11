package io.futakotome.stock.domain;

import java.util.Arrays;
import java.util.List;

public class MarketAggregator implements RequestPlateInfo {
    private static final List<RequestPlateInfo> allMarket = Arrays.asList(
            new HKMarket(),
            new BigASHMarkert(),
            new BigASZMarket(),
            new USMarket(),
            new SGMarket(),
            new JPMarket()
    );

    @Override
    public void sendPlateInfoRequest() {
        for (RequestPlateInfo perMarker : allMarket) {
            perMarker.sendPlateInfoRequest();
        }
    }
}
