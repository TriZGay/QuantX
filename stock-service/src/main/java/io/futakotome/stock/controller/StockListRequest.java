package io.futakotome.stock.controller;

public class StockListRequest extends PaginationRequest {
    private Integer market;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }
}
