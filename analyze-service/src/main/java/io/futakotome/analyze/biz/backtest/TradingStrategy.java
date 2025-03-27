package io.futakotome.analyze.biz.backtest;

import java.util.List;

public interface TradingStrategy {
    List<TradeSignal> generateSignals(List<MarketData> data);
}
