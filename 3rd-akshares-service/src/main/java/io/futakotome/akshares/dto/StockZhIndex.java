package io.futakotome.akshares.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockZhIndex {
    @JsonProperty("序号")
    private Long id;
    @JsonProperty("代码")
    private String code;
    @JsonProperty("名称")
    private String name;
    @JsonProperty("最新价")
    private Double price;
    @JsonProperty("涨跌幅")
    private Double ratio;
    @JsonProperty("涨跌额")
    private Double ratioVal;
    @JsonProperty("成交量")
    private Double turnover;
    @JsonProperty("成交额")
    private Double volume;
    @JsonProperty("振幅")
    private Double amplitude;
    @JsonProperty("最高")
    private Double high;
    @JsonProperty("最低")
    private Double low;
    @JsonProperty("今开")
    private Double open;
    @JsonProperty("昨收")
    private Double close;
    @JsonProperty("量比")
    private Double equivalentRatio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Double getRatioVal() {
        return ratioVal;
    }

    public void setRatioVal(Double ratioVal) {
        this.ratioVal = ratioVal;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getEquivalentRatio() {
        return equivalentRatio;
    }

    public void setEquivalentRatio(Double equivalentRatio) {
        this.equivalentRatio = equivalentRatio;
    }

    @Override
    public String toString() {
        return "StockZhIndex{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", ratio=" + ratio +
                ", ratioVal=" + ratioVal +
                ", turnover=" + turnover +
                ", volume=" + volume +
                ", amplitude=" + amplitude +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", close=" + close +
                ", equivalentRatio=" + equivalentRatio +
                '}';
    }
}
