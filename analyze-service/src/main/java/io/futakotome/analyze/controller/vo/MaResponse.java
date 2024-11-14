package io.futakotome.analyze.controller.vo;

public class MaResponse {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double maValue;
    private String datetime;

    public MaResponse(Integer market, String code, Integer rehabType, Double maValue, String datetime) {
        this.market = market;
        this.code = code;
        this.rehabType = rehabType;
        this.maValue = maValue;
        this.datetime = datetime;
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

    public Integer getRehabType() {
        return rehabType;
    }

    public void setRehabType(Integer rehabType) {
        this.rehabType = rehabType;
    }

    public Double getMaValue() {
        return maValue;
    }

    public void setMaValue(Double maValue) {
        this.maValue = maValue;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
