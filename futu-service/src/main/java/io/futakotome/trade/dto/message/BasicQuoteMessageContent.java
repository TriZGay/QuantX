package io.futakotome.trade.dto.message;

public class BasicQuoteMessageContent {
    private BasicQuoteSecurity security;
    private boolean isSuspended;
    private String listTime;
    private Double priceSpread;
    private String updateTime;
    private Double highPrice;
    private Double openPrice;
    private Double lowPrice;
    private Double curPrice;
    private Double lastClosePrice;
    private Long volume;
    private Double turnover;
    private Double turnoverRate;
    private Double amplitude;
    private Integer darkStatus;
    private Integer secStatus;

    public BasicQuoteSecurity getSecurity() {
        return security;
    }

    public void setSecurity(BasicQuoteSecurity security) {
        this.security = security;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
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

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
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

    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
    }

    public Integer getDarkStatus() {
        return darkStatus;
    }

    public void setDarkStatus(Integer darkStatus) {
        this.darkStatus = darkStatus;
    }

    public Integer getSecStatus() {
        return secStatus;
    }

    public void setSecStatus(Integer secStatus) {
        this.secStatus = secStatus;
    }

}
