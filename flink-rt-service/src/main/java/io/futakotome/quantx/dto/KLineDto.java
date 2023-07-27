package io.futakotome.quantx.dto;

public class KLineDto {
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

    public void setRehabType(Integer rehabType) {
        this.rehabType = rehabType;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public void setLastClosePrice(Double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public void setTurnoverRate(Double turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public void setPe(Double pe) {
        this.pe = pe;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
