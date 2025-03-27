package io.futakotome.analyze.service;

import io.futakotome.analyze.biz.backtest.BackTestEngine;
import io.futakotome.analyze.biz.backtest.MarketData;
import io.futakotome.analyze.biz.backtest.MovingAverageStrategy;
import io.futakotome.analyze.controller.vo.BackTestRequest;
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

    public String backTest(BackTestRequest backTestRequest) {
        switch (backTestRequest.getGranularity()) {
            case 1:
                //1分k
                List<MarketData> marketData = kLineMapper.queryKLineArchived(new KLineDto(KL_MIN_1_ARC_TABLE_NAME, backTestRequest.getCode(),
                                backTestRequest.getRehabType(), backTestRequest.getStart(), backTestRequest.getEnd()))
                        .stream()
                        .map(kLineDto -> new MarketData(kLineDto.getUpdateTime(), kLineDto.getOpenPrice(), kLineDto.getHighPrice(), kLineDto.getLowPrice(), kLineDto.getClosePrice()))
                        .collect(Collectors.toList());
                BackTestEngine backTestEngine = new BackTestEngine(1_000_000D, 0.01);
                backTestEngine.run(new MovingAverageStrategy(5, 20), marketData);
        }
        return "成功";
    }
}
