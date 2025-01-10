package io.futakotome.analyze.controller.vo;

public class BollResponse {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double ma20Mid;
    private Double doubleUpper;
    private Double doubleLower;
    private Double oneUpper;
    private Double oneLower;
    private Double tripleUpper;
    private Double tripleLower;
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

    public Double getMa20Mid() {
        return ma20Mid;
    }

    public void setMa20Mid(Double ma20Mid) {
        this.ma20Mid = ma20Mid;
    }

    public Double getDoubleUpper() {
        return doubleUpper;
    }

    public void setDoubleUpper(Double doubleUpper) {
        this.doubleUpper = doubleUpper;
    }

    public Double getDoubleLower() {
        return doubleLower;
    }

    public void setDoubleLower(Double doubleLower) {
        this.doubleLower = doubleLower;
    }

    public Double getOneUpper() {
        return oneUpper;
    }

    public void setOneUpper(Double oneUpper) {
        this.oneUpper = oneUpper;
    }

    public Double getOneLower() {
        return oneLower;
    }

    public void setOneLower(Double oneLower) {
        this.oneLower = oneLower;
    }

    public Double getTripleUpper() {
        return tripleUpper;
    }

    public void setTripleUpper(Double tripleUpper) {
        this.tripleUpper = tripleUpper;
    }

    public Double getTripleLower() {
        return tripleLower;
    }

    public void setTripleLower(Double tripleLower) {
        this.tripleLower = tripleLower;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
