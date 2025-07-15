package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockRTPrice;

import java.util.List;

public class StockZhRtResponse {
    private List<StockRTPrice> rtPrices;

    public List<StockRTPrice> getRtPrices() {
        return rtPrices;
    }

    public void setRtPrices(List<StockRTPrice> rtPrices) {
        this.rtPrices = rtPrices;
    }
}
