package io.futakotome.quantx.dto;

public class KLineDto  {
    private Integer market;
    private String code;
    private Integer rehabType;
    private Double highPrice;
    private Double openPrice;
    private Double lowPrice;
    private Double closePrice;
    private Double lastClosePrice;
    private Long volume;
    private Double turnover;
    private Double turnoverRate;
    private Double pe;
    private Double changeRate;
    private String updateTime;
    private String addTime;

    public KLineDto() {
    }

    public KLineDto(Integer market, String code, Integer rehabType, Double highPrice, Double openPrice, Double lowPrice, Double closePrice, Double lastClosePrice, Long volume, Double turnover, Double turnoverRate, Double pe, Double changeRate, String updateTime, String addTime) {
        this.market = market;
        this.code = code;
        this.rehabType = rehabType;
        this.highPrice = highPrice;
        this.openPrice = openPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.lastClosePrice = lastClosePrice;
        this.volume = volume;
        this.turnover = turnover;
        this.turnoverRate = turnoverRate;
        this.pe = pe;
        this.changeRate = changeRate;
        this.updateTime = updateTime;
        this.addTime = addTime;
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

    public Integer getRehabType() {
        return rehabType;
    }

    public void setRehabType(Integer rehabType) {
        this.rehabType = rehabType;
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

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(Double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
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

    public Double getPe() {
        return pe;
    }

    public void setPe(Double pe) {
        this.pe = pe;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "KLineDto{" +
                "market=" + market +
                ", code='" + code + '\'' +
                ", rehabType=" + rehabType +
                ", highPrice=" + highPrice +
                ", openPrice=" + openPrice +
                ", lowPrice=" + lowPrice +
                ", closePrice=" + closePrice +
                ", lastClosePrice=" + lastClosePrice +
                ", volume=" + volume +
                ", turnover=" + turnover +
                ", turnoverRate=" + turnoverRate +
                ", pe=" + pe +
                ", changeRate=" + changeRate +
                ", updateTime='" + updateTime + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}
