package io.futakotome.analyze.service;

import io.futakotome.analyze.biz.KLine;
import io.futakotome.analyze.biz.backtest.*;
import io.futakotome.analyze.controller.vo.BackTestOvrResponse;
import io.futakotome.analyze.controller.vo.BackTestRequest;
import io.futakotome.analyze.controller.vo.BackTestResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.dto.KLineDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static io.futakotome.analyze.mapper.KLineMapper.KL_MIN_1_ARC_TABLE_NAME;

@Service
public class BackTestService {
    private final KLineMapper kLineMapper;

    public BackTestService(KLineMapper kLineMapper) {
        this.kLineMapper = kLineMapper;
    }

    public BackTestResponse backTest(BackTestRequest backTestRequest) {
        switch (backTestRequest.getGranularity()) {
            case 1:
                //1分k
                BackTestResponse backTestResponse = new BackTestResponse();
                List<KLineDto> kLineDtos = kLineMapper.queryKLineArchived(new KLineDto(KL_MIN_1_ARC_TABLE_NAME, backTestRequest.getCode(),
                        backTestRequest.getRehabType(), backTestRequest.getStart(), backTestRequest.getEnd()));
                List<MarketData> marketData = kLineDtos.stream()
                        .map(kLineDto -> new MarketData(kLineDto.getUpdateTime(), kLineDto.getOpenPrice(), kLineDto.getHighPrice(), kLineDto.getLowPrice(), kLineDto.getClosePrice()))
                        .collect(Collectors.toList());
                BackTestEngine backTestEngine = new BackTestEngine(backTestRequest.getInitialCapital(), backTestRequest.getCommission());
                BackTestResult backTestResult = backTestEngine.run(new MovingAverageStrategy(5, 20), marketData);
                backTestResponse.setPrices(kLineDtos.stream().map(KLine::dto2Resp)
                        .collect(Collectors.toList()));
                backTestResponse.setTradeSignals(backTestResult.getSignals()
                        .stream().map(TradeSignal::dto2Vo).collect(Collectors.toList()));
                backTestResponse.setBackTestOvr(new BackTestOvrResponse(
                        backTestResult.getBackTestOvr().getFinalValue(),
                        backTestResult.getBackTestOvr().getTotalProfit(),
                        backTestResult.getBackTestOvr().getInitialCapital(),
                        backTestResult.getBackTestOvr().getCommission()
                ));
                return backTestResponse;
        }
        return new BackTestResponse();
    }
}
