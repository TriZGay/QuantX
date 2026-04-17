package io.futakotome.trade.dto.message;

public class CommonStaticInfo {
    private Integer market;
    private Integer stockType;

    public CommonStaticInfo(Integer market, Integer stockType) {
        this.market = market;
        this.stockType = stockType;
    }

    public CommonStaticInfo() {
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }
}
