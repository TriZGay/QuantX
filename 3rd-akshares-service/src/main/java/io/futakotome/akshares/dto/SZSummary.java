package io.futakotome.akshares.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SZSummary {
    @JsonProperty("证券类别")
    private String type;
    @JsonProperty("数量")
    private Long num;
    @JsonProperty("成交金额")
    private Double turnoverVal;
    @JsonProperty("总市值")
    private Double totalMarketVal;
    @JsonProperty("流通市值")
    private Double circularMarketVal;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Double getTurnoverVal() {
        return turnoverVal;
    }

    public void setTurnoverVal(Double turnoverVal) {
        this.turnoverVal = turnoverVal;
    }

    public Double getTotalMarketVal() {
        return totalMarketVal;
    }

    public void setTotalMarketVal(Double totalMarketVal) {
        this.totalMarketVal = totalMarketVal;
    }

    public Double getCircularMarketVal() {
        return circularMarketVal;
    }

    public void setCircularMarketVal(Double circularMarketVal) {
        this.circularMarketVal = circularMarketVal;
    }
}
