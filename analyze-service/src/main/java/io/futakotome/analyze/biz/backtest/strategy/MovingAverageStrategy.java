package io.futakotome.analyze.biz.backtest.strategy;

import io.futakotome.analyze.biz.backtest.AbstractStrategyHandler;
import io.futakotome.analyze.biz.backtest.StrategyType;
import io.futakotome.analyze.biz.backtest.TradeSignal;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.dto.KLineDto;
import io.futakotome.analyze.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.futakotome.analyze.mapper.KLineMapper.KL_MIN_1_ARC_TABLE_NAME;

@Component
@StrategyType(1)
public class MovingAverageStrategy extends AbstractStrategyHandler {
    private final KLineMapper kLineMapper;

    public MovingAverageStrategy(KLineMapper kLineMapper) {
        this.kLineMapper = kLineMapper;
    }

    public List<TradeSignal> generateMin1TradeSignals(String code, Integer rehabType, String start, String end) {
        int shortWindow = 5;
        int longWindow = 20;
        List<KLineDto> kLines = kLineMapper.queryKLineArchived(new KLineDto(KL_MIN_1_ARC_TABLE_NAME, code, rehabType, start, end));
        List<TradeSignal> signals = new ArrayList<>();
        List<Double> closes = kLines.stream().map(KLineDto::getClosePrice).collect(Collectors.toList());
        for (int i = longWindow; i < kLines.size(); i++) {
            Double shortMa = calculateMa(closes, i - shortWindow + 1, i);
            Double longMa = calculateMa(closes, i - longWindow + 1, i);
            if (shortMa > longMa) {
                signals.add(new TradeSignal(
                        kLines.get(i).getUpdateTime(),
                        TradeSignal.Action.BUY,
                        kLines.get(i).getClosePrice(),
                        100,
                        kLines.get(i).getOpenPrice(),
                        kLines.get(i).getClosePrice(),
                        kLines.get(i).getHighPrice(),
                        kLines.get(i).getLowPrice(),
                        kLines.get(i).getVolume()
                ));
            } else if (shortMa < longMa) {
                signals.add(new TradeSignal(
                        kLines.get(i).getUpdateTime(),
                        TradeSignal.Action.SELL,
                        kLines.get(i).getClosePrice(),
                        100,
                        kLines.get(i).getOpenPrice(),
                        kLines.get(i).getClosePrice(),
                        kLines.get(i).getHighPrice(),
                        kLines.get(i).getLowPrice(),
                        kLines.get(i).getVolume()
                ));
            }
        }
        fillSignals(signals, kLines);
        return signals;
    }

    private Double calculateMa(List<Double> prices, int start, int end) {
        return prices.subList(start, end + 1)
                .stream().mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

    private void fillSignals(List<TradeSignal> signals, List<KLineDto> kLineDtos) {
        List<String> signalDates = signals.stream().map(TradeSignal::getDatetime).collect(Collectors.toList());
        for (KLineDto klineDto : kLineDtos) {
            if (!signalDates.contains(klineDto.getUpdateTime())) {
                signals.add(new TradeSignal(
                        klineDto.getUpdateTime(),
                        TradeSignal.Action.HOLD,
                        null,
                        null,
                        klineDto.getOpenPrice(),
                        klineDto.getClosePrice(),
                        klineDto.getHighPrice(),
                        klineDto.getLowPrice(),
                        klineDto.getVolume()
                ));
            }
        }
        signals.sort((o1, o2) -> {
            LocalDateTime d1 = LocalDateTime.parse(o1.getDatetime(), DateUtils.DATE_TIME_WITH_MILLISECOND_FORMATTER);
            LocalDateTime d2 = LocalDateTime.parse(o2.getDatetime(), DateUtils.DATE_TIME_WITH_MILLISECOND_FORMATTER);
            return d1.compareTo(d2);
        });
    }

    @Override
    public List<TradeSignal> handle(Integer granularity, String code, Integer rehabType, String start, String end) {
        switch (granularity) {
            case 1:
                return generateMin1TradeSignals(code, rehabType, start, end);
            default:
                return List.of();
        }
    }
}
