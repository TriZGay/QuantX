package io.futakotome.common.message;

public class RTTickerMessage {
    private Integer market;
    private String code;
    private Long sequence;
    private Integer tickerDirection;
    private Double price;
    private Long volume;
    private Double turnover;
    private Integer tickerType;
    private Integer typeSign;
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

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Integer getTickerDirection() {
        return tickerDirection;
    }

    public void setTickerDirection(Integer tickerDirection) {
        this.tickerDirection = tickerDirection;
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

    public Integer getTickerType() {
        return tickerType;
    }

    public void setTickerType(Integer tickerType) {
        this.tickerType = tickerType;
    }

    public Integer getTypeSign() {
        return typeSign;
    }

    public void setTypeSign(Integer typeSign) {
        this.typeSign = typeSign;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
