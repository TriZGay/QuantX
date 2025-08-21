package io.futakotome.analyze.service;

import io.futakotome.analyze.biz.backtest.*;
import io.futakotome.analyze.controller.vo.BackTestRequest;
import io.futakotome.analyze.controller.vo.BackTestResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackTestService {
    private final StrategyContext strategyContext;

    public BackTestService(StrategyContext strategyContext) {
        this.strategyContext = strategyContext;
    }

    public BackTestResponse backTest(BackTestRequest backTestRequest) {
        AbstractStrategyHandler strategyHandler = strategyContext.getInstance(backTestRequest.getStrategyType());
        List<TradeSignal> tradeSignals = strategyHandler.handle(backTestRequest.getGranularity(),
                backTestRequest.getCode(), backTestRequest.getRehabType(),
                backTestRequest.getStart(), backTestRequest.getEnd());
        BackTestEngine backTestEngine = new BackTestEngine(backTestRequest.getInitialCapital(), backTestRequest.getCommission());
        BackTestResult result = backTestEngine.run(tradeSignals);
        BackTestResponse backTestResponse = new BackTestResponse();
        backTestResponse.setTradeSignals(result.tradeSignalsVo());
        backTestResponse.setBackTestOvr(result.getBackTestOvr().toVo());
        return backTestResponse;
    }
}
