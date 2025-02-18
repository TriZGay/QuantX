package io.futakotome.trade.dto.message;

public class MarketStateContent {
    private Integer marketHK;
    private Integer marketUS;
    private Integer marketSH;
    private Integer marketSZ;
    private Integer marketHKFuture;
    private Long time;
    private Long localTime;
    private Integer marketUSFuture;
    private Integer marketSGFuture;
    private Integer marketJPFuture;

    public Integer getMarketHK() {
        return marketHK;
    }

    public void setMarketHK(Integer marketHK) {
        this.marketHK = marketHK;
    }

    public Integer getMarketUS() {
        return marketUS;
    }

    public void setMarketUS(Integer marketUS) {
        this.marketUS = marketUS;
    }

    public Integer getMarketSH() {
        return marketSH;
    }

    public void setMarketSH(Integer marketSH) {
        this.marketSH = marketSH;
    }

    public Integer getMarketSZ() {
        return marketSZ;
    }

    public void setMarketSZ(Integer marketSZ) {
        this.marketSZ = marketSZ;
    }

    public Integer getMarketHKFuture() {
        return marketHKFuture;
    }

    public void setMarketHKFuture(Integer marketHKFuture) {
        this.marketHKFuture = marketHKFuture;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Long localTime) {
        this.localTime = localTime;
    }

    public Integer getMarketUSFuture() {
        return marketUSFuture;
    }

    public void setMarketUSFuture(Integer marketUSFuture) {
        this.marketUSFuture = marketUSFuture;
    }

    public Integer getMarketSGFuture() {
        return marketSGFuture;
    }

    public void setMarketSGFuture(Integer marketSGFuture) {
        this.marketSGFuture = marketSGFuture;
    }

    public Integer getMarketJPFuture() {
        return marketJPFuture;
    }

    public void setMarketJPFuture(Integer marketJPFuture) {
        this.marketJPFuture = marketJPFuture;
    }

    @Override
    public String toString() {
        return "MarketStateVo{" +
                "marketHK=" + marketHK +
                ", marketUS=" + marketUS +
                ", marketSH=" + marketSH +
                ", marketSZ=" + marketSZ +
                ", marketHKFuture=" + marketHKFuture +
                ", time=" + time +
                ", localTime=" + localTime +
                ", marketUSFuture=" + marketUSFuture +
                ", marketSGFuture=" + marketSGFuture +
                ", marketJPFuture=" + marketJPFuture +
                '}';
    }
}
