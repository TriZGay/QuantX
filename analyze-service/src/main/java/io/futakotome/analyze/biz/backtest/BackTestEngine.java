package io.futakotome.analyze.biz.backtest;

import java.util.List;

public class BackTestEngine {
    private final Double initialCapital;
    private final Double commission;

    public BackTestEngine(Double initialCapital, Double commission) {
        this.initialCapital = initialCapital;
        this.commission = commission;
    }

    public void run(TradingStrategy strategy, List<MarketData> data) {
        List<TradeSignal> signals = strategy.generateSignals(data);
        Double cash = initialCapital;
        int position = 0;
        Double totalProfit = 0D;

        for (TradeSignal signal : signals) {
            Double cost = signal.getPrice() * signal.getQuantity() + commission;
            if (TradeSignal.Action.BUY.equals(signal.getAction()) && cash >= cost) {
                cash -= cost;
                position += signal.getQuantity();
                System.out.printf("%s买入%d股 @ %.2f\n", signal.getDatetime(), signal.getQuantity(), signal.getPrice());
            } else if (TradeSignal.Action.SELL.equals(signal.getAction()) && position >= signal.getQuantity()) {
                cash += signal.getPrice() * signal.getQuantity() - commission;
                position -= signal.getQuantity();
                System.out.printf("%s卖出%d股 @ %.2f\n", signal.getDatetime(), signal.getQuantity(), signal.getPrice());
            }
        }
        Double finalValue = cash + position * data.get(data.size() - 1).getClose();
        totalProfit = finalValue - initialCapital;
        System.out.printf("\n初始资金:%.2f\n最终价值:%.2f\n总收益:%.2f (%.2f%%)", initialCapital, finalValue, totalProfit, (totalProfit / initialCapital) * 100);

    }
}
