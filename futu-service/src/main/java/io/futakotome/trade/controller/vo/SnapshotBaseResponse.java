package io.futakotome.trade.controller.vo;

public class SnapshotBaseResponse {
    private String market;
    private String code;
    private String name;
    private String type;
    private String isSuspend;
    private String listTime;
    private Integer lotSize;
    private Double priceSpread;
    private String updateTime;
    private Double highPrice;
    private Double openPrice;
    private Double lowPrice;
    private Double lastClosePrice;
    private Double curPrice;
    private Long volume;
    private Double turnover;
    private Double turnoverRate;
    private Double askPrice;
    private Double bidPrice;
    private Long askVol;
    private Long bidVol;
    private Double amplitude;
    private Double avgPrice;
    private Double bidAskRatio;
    private Double volumeRatio;
    private Double highest52WeeksPrice;
    private Double lowest52WeeksPrice;
    private Double highestHistoryPrice;
    private Double lowestHistoryPrice;
    private Double prePrice;
    private Double preHighPrice;
    private Double preLowPrice;
    private Long preVolume;
    private Double preTurnover;
    private Double preChangeVal;
    private Double preChangeRate;
    private Double preAmplitude;
    private Double afterPrice;
    private Double afterHighPrice;
    private Double afterLowPrice;
    private Long afterVolume;
    private Double afterTurnover;
    private Double afterChangeVal;
    private Double afterChangeRate;
    private Double afterAmplitude;
    private String secStatus;
    private Double closePrice5Minute;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsSuspend() {
        return isSuspend;
    }

    public void setIsSuspend(String isSuspend) {
        this.isSuspend = isSuspend;
    }

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public Double getPriceSpread() {
        return priceSpread;
    }

    public void setPriceSpread(Double priceSpread) {
        this.priceSpread = priceSpread;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(Double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Double turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Long getAskVol() {
        return askVol;
    }

    public void setAskVol(Long askVol) {
        this.askVol = askVol;
    }

    public Long getBidVol() {
        return bidVol;
    }

    public void setBidVol(Long bidVol) {
        this.bidVol = bidVol;
    }

    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getBidAskRatio() {
        return bidAskRatio;
    }

    public void setBidAskRatio(Double bidAskRatio) {
        this.bidAskRatio = bidAskRatio;
    }

    public Double getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(Double volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    public Double getHighest52WeeksPrice() {
        return highest52WeeksPrice;
    }

    public void setHighest52WeeksPrice(Double highest52WeeksPrice) {
        this.highest52WeeksPrice = highest52WeeksPrice;
    }

    public Double getLowest52WeeksPrice() {
        return lowest52WeeksPrice;
    }

    public void setLowest52WeeksPrice(Double lowest52WeeksPrice) {
        this.lowest52WeeksPrice = lowest52WeeksPrice;
    }

    public Double getHighestHistoryPrice() {
        return highestHistoryPrice;
    }

    public void setHighestHistoryPrice(Double highestHistoryPrice) {
        this.highestHistoryPrice = highestHistoryPrice;
    }

    public Double getLowestHistoryPrice() {
        return lowestHistoryPrice;
    }

    public void setLowestHistoryPrice(Double lowestHistoryPrice) {
        this.lowestHistoryPrice = lowestHistoryPrice;
    }

    public Double getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(Double prePrice) {
        this.prePrice = prePrice;
    }

    public Double getPreHighPrice() {
        return preHighPrice;
    }

    public void setPreHighPrice(Double preHighPrice) {
        this.preHighPrice = preHighPrice;
    }

    public Double getPreLowPrice() {
        return preLowPrice;
    }

    public void setPreLowPrice(Double preLowPrice) {
        this.preLowPrice = preLowPrice;
    }

    public Long getPreVolume() {
        return preVolume;
    }

    public void setPreVolume(Long preVolume) {
        this.preVolume = preVolume;
    }

    public Double getPreTurnover() {
        return preTurnover;
    }

    public void setPreTurnover(Double preTurnover) {
        this.preTurnover = preTurnover;
    }

    public Double getPreChangeVal() {
        return preChangeVal;
    }

    public void setPreChangeVal(Double preChangeVal) {
        this.preChangeVal = preChangeVal;
    }

    public Double getPreChangeRate() {
        return preChangeRate;
    }

    public void setPreChangeRate(Double preChangeRate) {
        this.preChangeRate = preChangeRate;
    }

    public Double getPreAmplitude() {
        return preAmplitude;
    }

    public void setPreAmplitude(Double preAmplitude) {
        this.preAmplitude = preAmplitude;
    }

    public Double getAfterPrice() {
        return afterPrice;
    }

    public void setAfterPrice(Double afterPrice) {
        this.afterPrice = afterPrice;
    }

    public Double getAfterHighPrice() {
        return afterHighPrice;
    }

    public void setAfterHighPrice(Double afterHighPrice) {
        this.afterHighPrice = afterHighPrice;
    }

    public Double getAfterLowPrice() {
        return afterLowPrice;
    }

    public void setAfterLowPrice(Double afterLowPrice) {
        this.afterLowPrice = afterLowPrice;
    }

    public Long getAfterVolume() {
        return afterVolume;
    }

    public void setAfterVolume(Long afterVolume) {
        this.afterVolume = afterVolume;
    }

    public Double getAfterTurnover() {
        return afterTurnover;
    }

    public void setAfterTurnover(Double afterTurnover) {
        this.afterTurnover = afterTurnover;
    }

    public Double getAfterChangeVal() {
        return afterChangeVal;
    }

    public void setAfterChangeVal(Double afterChangeVal) {
        this.afterChangeVal = afterChangeVal;
    }

    public Double getAfterChangeRate() {
        return afterChangeRate;
    }

    public void setAfterChangeRate(Double afterChangeRate) {
        this.afterChangeRate = afterChangeRate;
    }

    public Double getAfterAmplitude() {
        return afterAmplitude;
    }

    public void setAfterAmplitude(Double afterAmplitude) {
        this.afterAmplitude = afterAmplitude;
    }

    public String getSecStatus() {
        return secStatus;
    }

    public void setSecStatus(String secStatus) {
        this.secStatus = secStatus;
    }

    public Double getClosePrice5Minute() {
        return closePrice5Minute;
    }

    public void setClosePrice5Minute(Double closePrice5Minute) {
        this.closePrice5Minute = closePrice5Minute;
    }
}
