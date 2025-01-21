package io.futakotome.analyze.controller.vo;

public class EmaResponse {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double ema5Value;
    private Double ema10Value;
    private Double ema20Value;
    private Double ema60Value;
    private Double ema120Value;
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

    public Double getEma5Value() {
        return ema5Value;
    }

    public void setEma5Value(Double ema5Value) {
        this.ema5Value = ema5Value;
    }

    public Double getEma10Value() {
        return ema10Value;
    }

    public void setEma10Value(Double ema10Value) {
        this.ema10Value = ema10Value;
    }

    public Double getEma20Value() {
        return ema20Value;
    }

    public void setEma20Value(Double ema20Value) {
        this.ema20Value = ema20Value;
    }

    public Double getEma60Value() {
        return ema60Value;
    }

    public void setEma60Value(Double ema60Value) {
        this.ema60Value = ema60Value;
    }

    public Double getEma120Value() {
        return ema120Value;
    }

    public void setEma120Value(Double ema120Value) {
        this.ema120Value = ema120Value;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
