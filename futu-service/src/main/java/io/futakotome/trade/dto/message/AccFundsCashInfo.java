package io.futakotome.trade.dto.message;

public class AccFundsCashInfo {
    private Integer currency;        // 货币类型，取值参考 Currency
    private Double cash;           // 现金结余
    private Double availableBalance;   // 现金可提金额
    private Double netCashPower;        // 现金购买力

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getNetCashPower() {
        return netCashPower;
    }

    public void setNetCashPower(Double netCashPower) {
        this.netCashPower = netCashPower;
    }
}
