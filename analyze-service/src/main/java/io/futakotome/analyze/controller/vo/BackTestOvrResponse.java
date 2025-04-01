package io.futakotome.analyze.controller.vo;

public class BackTestOvrResponse {
    private Double finalValue;
    private Double totalProfit;
    private Double initialCapital;
    private Double commission;

    public BackTestOvrResponse(Double finalValue, Double totalProfit, Double initialCapital, Double commission) {
        this.finalValue = finalValue;
        this.totalProfit = totalProfit;
        this.initialCapital = initialCapital;
        this.commission = commission;
    }

    public BackTestOvrResponse() {
    }

    public Double getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Double finalValue) {
        this.finalValue = finalValue;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getInitialCapital() {
        return initialCapital;
    }

    public void setInitialCapital(Double initialCapital) {
        this.initialCapital = initialCapital;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }
}
