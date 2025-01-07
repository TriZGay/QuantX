package io.futakotome.analyze.controller.vo;

public class MaResponse {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double ma5Value;
    private Double ma10Value;
    private Double ma20Value;
    private Double ma30Value;
    private Double ma60Value;
    private Double ma120Value;
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

    public Double getMa5Value() {
        return ma5Value;
    }

    public void setMa5Value(Double ma5Value) {
        this.ma5Value = ma5Value;
    }

    public Double getMa10Value() {
        return ma10Value;
    }

    public void setMa10Value(Double ma10Value) {
        this.ma10Value = ma10Value;
    }

    public Double getMa20Value() {
        return ma20Value;
    }

    public void setMa20Value(Double ma20Value) {
        this.ma20Value = ma20Value;
    }

    public Double getMa30Value() {
        return ma30Value;
    }

    public void setMa30Value(Double ma30Value) {
        this.ma30Value = ma30Value;
    }

    public Double getMa60Value() {
        return ma60Value;
    }

    public void setMa60Value(Double ma60Value) {
        this.ma60Value = ma60Value;
    }

    public Double getMa120Value() {
        return ma120Value;
    }

    public void setMa120Value(Double ma120Value) {
        this.ma120Value = ma120Value;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
