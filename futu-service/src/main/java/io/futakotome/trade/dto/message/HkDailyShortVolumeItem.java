package io.futakotome.trade.dto.message;

public class HkDailyShortVolumeItem {
    private Long timestamp;
    private String timestampStr;
    private Long sharesTraded;
    private Long turnover;
    private Long shortSellSharesTraded;
    private Long shortSellTurnover;
    private Double openPrice;
    private Double closePrice;
    private Double lastClosePrice;
    private Double dailyTradeAvgRatio;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampStr() {
        return timestampStr;
    }

    public void setTimestampStr(String timestampStr) {
        this.timestampStr = timestampStr;
    }

    public Long getSharesTraded() {
        return sharesTraded;
    }

    public void setSharesTraded(Long sharesTraded) {
        this.sharesTraded = sharesTraded;
    }

    public Long getTurnover() {
        return turnover;
    }

    public void setTurnover(Long turnover) {
        this.turnover = turnover;
    }

    public Long getShortSellSharesTraded() {
        return shortSellSharesTraded;
    }

    public void setShortSellSharesTraded(Long shortSellSharesTraded) {
        this.shortSellSharesTraded = shortSellSharesTraded;
    }

    public Long getShortSellTurnover() {
        return shortSellTurnover;
    }

    public void setShortSellTurnover(Long shortSellTurnover) {
        this.shortSellTurnover = shortSellTurnover;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
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

    public Double getDailyTradeAvgRatio() {
        return dailyTradeAvgRatio;
    }

    public void setDailyTradeAvgRatio(Double dailyTradeAvgRatio) {
        this.dailyTradeAvgRatio = dailyTradeAvgRatio;
    }
}
