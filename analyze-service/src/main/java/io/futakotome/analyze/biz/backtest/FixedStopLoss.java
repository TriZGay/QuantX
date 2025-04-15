package io.futakotome.analyze.biz.backtest;

/**
 * 固定止损
 */
public class FixedStopLoss implements RiskManagement {
    private final Double stopLossPercent;

    public FixedStopLoss(Double stopLossPercent) {
        this.stopLossPercent = stopLossPercent;
    }

    @Override
    public Double getStopLoss() {
        return stopLossPercent;
    }

    @Override
    public Double getTakeProfit() {
        return 0.0;
    }
}
