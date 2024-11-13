package io.futakotome.trade.controller.vo;

public class StockListRequest extends PaginationRequest {
    private Integer market;
    private Integer exchangeType;
    private Integer delisting;
    private Integer stockType;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

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
