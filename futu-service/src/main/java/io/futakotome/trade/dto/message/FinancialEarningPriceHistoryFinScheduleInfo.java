package io.futakotome.trade.dto.message;

public class FinancialEarningPriceHistoryFinScheduleInfo {
    private Integer delta;
    private Double closePrice;

    public Integer getDelta() {
        return delta;
    }

    public void setDelta(Integer delta) {
        this.delta = delta;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }
}
