package io.futakotome.analyze.controller.vo;

public class MetaResponse {
    private Integer market;
    private String code;

    public MetaResponse(Integer market, String code) {
        this.market = market;
        this.code = code;
    }

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
