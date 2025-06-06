package io.futakotome.trade.controller.vo;

public class ListStockRequest extends PaginationRequest {
    private Integer market;
    private Integer delisting;
    private Integer stockType;
    private String name;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
