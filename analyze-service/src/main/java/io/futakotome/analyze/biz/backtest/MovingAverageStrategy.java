package io.futakotome.analyze.biz.backtest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovingAverageStrategy implements TradingStrategy {
    private final Integer shortWindow;
    private final Integer longWindow;

    public MovingAverageStrategy(Integer shortWindow, Integer longWindow) {
        this.shortWindow = shortWindow;
        this.longWindow = longWindow;
    }

    @Override
    public List<TradeSignal> generateSignals(List<MarketData> data) {
        List<TradeSignal> signals = new ArrayList<>();
        List<Double> closes = data.stream().map(MarketData::getClose).collect(Collectors.toList());
        for (int i = longWindow; i < data.size(); i++) {
            Double shortMa = calculateMa(closes, i - shortWindow + 1, i);
            Double longMa = calculateMa(closes, i - longWindow + 1, i);
            if (shortMa > longMa) {
                signals.add(new TradeSignal(
                        data.get(i).getDatetime(),
                        TradeSignal.Action.BUY,
                        data.get(i).getClose(),
                        100
                ));
            } else if (shortMa < longMa) {
                signals.add(new TradeSignal(
                        data.get(i).getDatetime(),
                        TradeSignal.Action.SELL,
                        data.get(i).getClose(),
                        100
                ));
            }
        }
        return signals;
    }

    private Double calculateMa(List<Double> prices, int start, int end) {
        return prices.subList(start, end + 1)
                .stream().mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }
}
