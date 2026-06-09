package io.futakotome.trade.dto.message;

import java.util.List;

public class FinancialRevenueBreakDownContent {
    private String period;
    private List<RevenueBreakdownGroupContent> breakdownList;
    private String currencyCode;
    private List<ScreenDateContent> screenDateList;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<RevenueBreakdownGroupContent> getBreakdownList() {
        return breakdownList;
    }

    public void setBreakdownList(List<RevenueBreakdownGroupContent> breakdownList) {
        this.breakdownList = breakdownList;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<ScreenDateContent> getScreenDateList() {
        return screenDateList;
    }

    public void setScreenDateList(List<ScreenDateContent> screenDateList) {
        this.screenDateList = screenDateList;
    }
}
