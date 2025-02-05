package io.futakotome.trade.dto.ws;

public class MarketStateWsMessage implements Message {
    private String marketHK;
    private String marketUS;
    private String marketSH;
    private String marketSZ;
    private String marketHKFuture;
    private String time;
    private String localTime;
    private String marketUSFuture;
    private String marketSGFuture;
    private String marketJPFuture;

    @Override
    public String toString() {
        return "MarketStateWsMessage{" +
                "marketHK='" + marketHK + '\'' +
                ", marketUS='" + marketUS + '\'' +
                ", marketSH='" + marketSH + '\'' +
                ", marketSZ='" + marketSZ + '\'' +
                ", marketHKFuture='" + marketHKFuture + '\'' +
                ", time='" + time + '\'' +
                ", localTime='" + localTime + '\'' +
                ", marketUSFuture='" + marketUSFuture + '\'' +
                ", marketSGFuture='" + marketSGFuture + '\'' +
                ", marketJPFuture='" + marketJPFuture + '\'' +
                '}';
    }

    public String getMarketHK() {
        return marketHK;
    }

    public void setMarketHK(String marketHK) {
        this.marketHK = marketHK;
    }

    public String getMarketUS() {
        return marketUS;
    }

    public void setMarketUS(String marketUS) {
        this.marketUS = marketUS;
    }

    public String getMarketSH() {
        return marketSH;
    }

    public void setMarketSH(String marketSH) {
        this.marketSH = marketSH;
    }

    public String getMarketSZ() {
        return marketSZ;
    }

    public void setMarketSZ(String marketSZ) {
        this.marketSZ = marketSZ;
    }

    public String getMarketHKFuture() {
        return marketHKFuture;
    }

    public void setMarketHKFuture(String marketHKFuture) {
        this.marketHKFuture = marketHKFuture;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getMarketUSFuture() {
        return marketUSFuture;
    }

    public void setMarketUSFuture(String marketUSFuture) {
        this.marketUSFuture = marketUSFuture;
    }

    public String getMarketSGFuture() {
        return marketSGFuture;
    }

    public void setMarketSGFuture(String marketSGFuture) {
        this.marketSGFuture = marketSGFuture;
    }

    public String getMarketJPFuture() {
        return marketJPFuture;
    }

    public void setMarketJPFuture(String marketJPFuture) {
        this.marketJPFuture = marketJPFuture;
    }

    @Override
    public MessageType getType() {
        return MessageType.MARKET_STATE;
    }
}
