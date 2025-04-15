package io.futakotome.analyze.biz.backtest;

public interface RiskManagement {
    Double getStopLoss();

    Double getTakeProfit();
}
