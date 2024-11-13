package io.futakotome.trade.message;

public class RealTimeTickerMessageContent {
    private Integer market;
    private String code;
    private String time;
    private Long sequence;
    private Integer dir;
    private Double price;
    private Long volume;
    private Double turnover;
    private Double recvTime;
    private Integer type;
    private Integer typeSign;
    private Integer pushDataType;
    private Double timestamp;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Integer getDir() {
        return dir;
    }

    public void setDir(Integer dir) {
        this.dir = dir;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Double getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Double recvTime) {
        this.recvTime = recvTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypeSign() {
        return typeSign;
    }

    public void setTypeSign(Integer typeSign) {
        this.typeSign = typeSign;
    }

    public Integer getPushDataType() {
        return pushDataType;
    }

    public void setPushDataType(Integer pushDataType) {
        this.pushDataType = pushDataType;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }
}
