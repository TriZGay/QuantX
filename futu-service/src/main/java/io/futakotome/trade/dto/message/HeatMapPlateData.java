package io.futakotome.trade.dto.message;

public class HeatMapPlateData {
    private CommonSecurity plate; //板块
    private String plateName; //板块名称
    private Double curPrice; //最新价
    private Double changeRate; //涨跌幅(%)
    private Double turnover; //成交额
    private Long volume; //成交量
    private Double marketVal; //市值
    private Double peAvg; //平均市盈率
    private Integer riseCount; //涨家数
    private Integer fallCount; //跌家数
    private Integer equalCount; //持平数
    private CommonSecurity leaderStock; //领涨股
    private String description; //板块描述

    public CommonSecurity getPlate() {
        return plate;
    }

    public void setPlate(CommonSecurity plate) {
        this.plate = plate;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getMarketVal() {
        return marketVal;
    }

    public void setMarketVal(Double marketVal) {
        this.marketVal = marketVal;
    }

    public Double getPeAvg() {
        return peAvg;
    }

    public void setPeAvg(Double peAvg) {
        this.peAvg = peAvg;
    }

    public Integer getRiseCount() {
        return riseCount;
    }

    public void setRiseCount(Integer riseCount) {
        this.riseCount = riseCount;
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

    public CommonSecurity getLeaderStock() {
        return leaderStock;
    }

    public void setLeaderStock(CommonSecurity leaderStock) {
        this.leaderStock = leaderStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
