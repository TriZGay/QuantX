package io.futakotome.analyze.controller.vo;

public class RsiResponse {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double rsi6;
    private Double rsi12;
    private Double rsi24;
    private String updateTime;

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

    public Double getRsi6() {
        return rsi6;
    }

    public void setRsi6(Double rsi6) {
        this.rsi6 = rsi6;
    }

    public Double getRsi12() {
        return rsi12;
    }

    public void setRsi12(Double rsi12) {
        this.rsi12 = rsi12;
    }

    public Double getRsi24() {
        return rsi24;
    }

    public void setRsi24(Double rsi24) {
        this.rsi24 = rsi24;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
