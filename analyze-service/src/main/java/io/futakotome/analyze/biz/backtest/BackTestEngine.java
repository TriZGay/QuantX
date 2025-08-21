package io.futakotome.analyze.biz.backtest;

import java.util.List;

public class BackTestEngine {
    private final Double initialCapital;
    private final Double commission;

    public BackTestEngine(Double initialCapital, Double commission) {
        this.initialCapital = initialCapital;
        this.commission = commission;
    }

    public BackTestResult run(List<TradeSignal> signals) {
        Double cash = initialCapital;
        int position = 0;
        Double totalProfit = 0D;

        for (TradeSignal signal : signals) {
            if (TradeSignal.Action.HOLD.equals(signal.getAction())) {
                System.out.println("持仓.");
            } else {
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
        }

        Double finalValue = cash + position * signals.get(signals.size() - 1).getClose();
        totalProfit = ((finalValue - initialCapital) / initialCapital) * 100;
        System.out.printf("\n初始资金:%.2f\n最终价值:%.2f\n总收益:%.2f%%", initialCapital, finalValue, totalProfit);
        BackTestResult backTestResult = new BackTestResult();
        backTestResult.setSignals(signals);
        backTestResult.setBackTestOvr(new BackTestResult.BackTestOvr(finalValue, totalProfit, initialCapital, commission));
        return backTestResult;
    }

}
