package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockItem;

import java.util.List;

public class BigAStockIndividual {
    private List<StockItem> stockItems;

    public List<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<StockItem> stockItems) {
        this.stockItems = stockItems;
    }
}
