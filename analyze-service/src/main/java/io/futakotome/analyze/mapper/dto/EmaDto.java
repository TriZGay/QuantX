package io.futakotome.analyze.mapper.dto;

public class EmaDto {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double ema_5;
    private Double ema_10;
    private Double ema_20;
    private Double ema_30;
    private Double ema_60;
    private Double ema_120;
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

    public Double getEma_5() {
        return ema_5;
    }

    public void setEma_5(Double ema_5) {
        this.ema_5 = ema_5;
    }

    public Double getEma_10() {
        return ema_10;
    }

    public void setEma_10(Double ema_10) {
        this.ema_10 = ema_10;
    }

    public Double getEma_20() {
        return ema_20;
    }

    public void setEma_20(Double ema_20) {
        this.ema_20 = ema_20;
    }

    public Double getEma_30() {
        return ema_30;
    }

    public void setEma_30(Double ema_30) {
        this.ema_30 = ema_30;
    }

    public Double getEma_60() {
        return ema_60;
    }

    public void setEma_60(Double ema_60) {
        this.ema_60 = ema_60;
    }

    public Double getEma_120() {
        return ema_120;
    }

    public void setEma_120(Double ema_120) {
        this.ema_120 = ema_120;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
