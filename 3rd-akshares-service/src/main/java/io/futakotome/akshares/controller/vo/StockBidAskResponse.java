package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockBidAskItem;

import java.util.List;

public class StockBidAskResponse {
    private List<StockBidAskItem> bidAskItems;

    public List<StockBidAskItem> getBidAskItems() {
        return bidAskItems;
    }

    public void setBidAskItems(List<StockBidAskItem> bidAskItems) {
        this.bidAskItems = bidAskItems;
    }
}
