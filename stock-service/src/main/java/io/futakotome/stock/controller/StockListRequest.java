package io.futakotome.stock.controller;

public class StockListRequest extends PaginationRequest {
    private Integer market;
    private Integer exchangeType;
    private Integer delisting;

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Integer getDelisting() {
        return delisting;
    }

    public void setDelisting(Integer delisting) {
        this.delisting = delisting;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }
}
