package io.futakotome.trade.dto.message;

public class FinancialEarningMovePricePerformance {
    private Long tradingDay;
    private String tradingDayStr;
    private Double closePrice;
    private Double openPrice;
    private Double highestPrice;
    private Double lowestPrice;
    private Double lastClosePrice;
    private Double optionIV;
    private Double optionHV;

    public Long getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(Long tradingDay) {
        this.tradingDay = tradingDay;
    }

    public String getTradingDayStr() {
        return tradingDayStr;
    }

    public void setTradingDayStr(String tradingDayStr) {
        this.tradingDayStr = tradingDayStr;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Double getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(Double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public Double getOptionIV() {
        return optionIV;
    }

    public void setOptionIV(Double optionIV) {
        this.optionIV = optionIV;
    }

    public Double getOptionHV() {
        return optionHV;
    }

    public void setOptionHV(Double optionHV) {
        this.optionHV = optionHV;
    }
}
