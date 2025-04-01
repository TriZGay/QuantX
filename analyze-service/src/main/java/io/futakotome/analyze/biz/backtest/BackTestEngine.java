package io.futakotome.analyze.biz.backtest;

import io.futakotome.analyze.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BackTestEngine {
    private final Double initialCapital;
    private final Double commission;

    public BackTestEngine(Double initialCapital, Double commission) {
        this.initialCapital = initialCapital;
        this.commission = commission;
    }

    public BackTestResult run(TradingStrategy strategy, List<MarketData> data) {
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
        totalProfit = ((finalValue - initialCapital) / initialCapital) * 100;
        System.out.printf("\n初始资金:%.2f\n最终价值:%.2f\n总收益:%.2f%%", initialCapital, finalValue, totalProfit);
        BackTestResult backTestResult = new BackTestResult();
        fillSignals(signals, data);
        backTestResult.setSignals(signals);
        backTestResult.setBackTestOvr(new BackTestResult.BackTestOvr(finalValue, totalProfit, initialCapital, commission));
        return backTestResult;
    }

    private void fillSignals(List<TradeSignal> signals, List<MarketData> data) {
        List<String> marketDates = data.stream().map(MarketData::getDatetime).collect(Collectors.toList());
        List<String> signalDates = signals.stream().map(TradeSignal::getDatetime).collect(Collectors.toList());
        for (String marketDate : marketDates) {
            if (!signalDates.contains(marketDate)) {
                signals.add(new TradeSignal(
                        marketDate,
                        TradeSignal.Action.NONE,
                        null,
                        null
                ));
            }
        }
        signals.sort((o1, o2) -> {
            LocalDateTime d1 = LocalDateTime.parse(o1.getDatetime(), DateUtils.DATE_TIME_FORMATTER);
            LocalDateTime d2 = LocalDateTime.parse(o2.getDatetime(), DateUtils.DATE_TIME_FORMATTER);
            return d1.compareTo(d2);
        });
    }

}
