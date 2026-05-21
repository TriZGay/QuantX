package io.futakotome.trade.controller.vo;

public class SnapshotPlateResponse {
    private Integer market;
    private String marketStr;
    private String code;
    private Integer raiseCount;
    private Integer fallCount;
    private Integer equalCount;
    private String updateTime;

    public String getMarketStr() {
        return marketStr;
    }

    public void setMarketStr(String marketStr) {
        this.marketStr = marketStr;
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

    public Integer getRaiseCount() {
        return raiseCount;
    }

    public void setRaiseCount(Integer raiseCount) {
        this.raiseCount = raiseCount;
    }

    public Integer getFallCount() {
        return fallCount;
    }

    public void setFallCount(Integer fallCount) {
        this.fallCount = fallCount;
    }

    public Integer getEqualCount() {
        return equalCount;
    }

    public void setEqualCount(Integer equalCount) {
        this.equalCount = equalCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
