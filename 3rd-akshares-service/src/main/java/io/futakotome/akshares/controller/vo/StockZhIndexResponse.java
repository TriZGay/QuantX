package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockZhIndex;

import java.util.List;

public class StockZhIndexResponse {
    private List<StockZhIndex> stockZhIndexList;

    public List<StockZhIndex> getStockZhIndexList() {
        return stockZhIndexList;
    }

    public void setStockZhIndexList(List<StockZhIndex> stockZhIndexList) {
        this.stockZhIndexList = stockZhIndexList;
    }
}
