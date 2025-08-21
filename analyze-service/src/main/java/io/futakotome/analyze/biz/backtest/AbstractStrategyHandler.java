package io.futakotome.analyze.biz.backtest;

import java.util.List;

public abstract class AbstractStrategyHandler {
    abstract public List<TradeSignal> handle(Integer granularity, String code, Integer rehabType, String start, String end);
}
