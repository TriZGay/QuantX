package io.futakotome.analyze.controller.vo;

public class KLineResponse {
    private Integer market;
    private String code;
    private Integer rehabType;
    private String highPrice;
    private String openPrice;
    private String lowPrice;
    private String closePrice;
    private Double lastClosePrice;
    private Long volume;
    private Double turnover;
    private Double turnoverRate;
    private Double pe;
    private Double changeRate;
    private String datetime;

    public KLineResponse(Integer market, String code, Integer rehabType, String highPrice, String openPrice, String lowPrice, String closePrice, Double lastClosePrice, Long volume, Double turnover, Double turnoverRate, Double pe, Double changeRate, String datetime) {
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
        this.datetime = datetime;
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

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
