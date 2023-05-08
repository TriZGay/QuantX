package io.futakotome.sub.controller;

public class ListSubscribeRequest {
    private Integer market;
    private String code;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
