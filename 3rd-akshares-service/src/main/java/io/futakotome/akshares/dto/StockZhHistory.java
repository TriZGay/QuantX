package io.futakotome.akshares.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockZhHistory {
    @JsonProperty("日期")
    private String date;
    @JsonProperty("股票代码")
    private String code;
    @JsonProperty("开盘")
    private Double open;
    @JsonProperty("收盘")
    private Double close;
    @JsonProperty("最高")
    private Double high;
    @JsonProperty("最低")
    private Double low;
    @JsonProperty("成交量")
    private Long turnover;
    @JsonProperty("成交额")
    private Double volume;
    @JsonProperty("振幅")
    private Double amplitude;
    @JsonProperty("涨跌幅")
    private Double chg;
    @JsonProperty("涨跌额")
    private Double change;
    @JsonProperty("换手率")
    private Double turnoverRatio;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getTurnover() {
        return turnover;
    }

    public void setTurnover(Long turnover) {
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

    public Double getChg() {
        return chg;
    }

    public void setChg(Double chg) {
        this.chg = chg;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getTurnoverRatio() {
        return turnoverRatio;
    }

    public void setTurnoverRatio(Double turnoverRatio) {
        this.turnoverRatio = turnoverRatio;
    }

    @Override
    public String toString() {
        return "StockZhHistory{" +
                "date='" + date + '\'' +
                ", code='" + code + '\'' +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", turnover=" + turnover +
                ", volume=" + volume +
                ", amplitude=" + amplitude +
                ", chg=" + chg +
                ", change=" + change +
                ", turnoverRatio=" + turnoverRatio +
                '}';
    }
}
