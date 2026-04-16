package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.SnapshotPriceChangeResponse;
import io.futakotome.analyze.mapper.StatisticsMapper;
import io.futakotome.analyze.mapper.dto.SnapshotPriceChangeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.futakotome.analyze.utils.DateUtils.DATE_TIME_FORMATTER;

public class Statistics {
    private static final Logger LOGGER = LoggerFactory.getLogger(Statistics.class);
    private final StatisticsMapper statisticsMapper;

    public Statistics(StatisticsMapper statisticsMapper) {
        this.statisticsMapper = statisticsMapper;
    }

    public SnapshotPriceChangeResponse heatmapByPlates(Integer market) {
        if (Objects.isNull(market)) {
            throw new IllegalArgumentException("市场参数为空");
        }
        SnapshotPriceChangeResponse response = new SnapshotPriceChangeResponse();
        response.setRaises(statisticsMapper.queryRiseTop10InPlatesByMarket(market)
                .stream().map(this::priceChangeDto2Vo).collect(Collectors.toList()));
        response.setReduces(statisticsMapper.queryReduceTop10InPlatesByMarket(market)
                .stream().map(this::priceChangeDto2Vo).collect(Collectors.toList()));
        return response;
    }

    private SnapshotPriceChangeResponse.PriceChangeResponse priceChangeDto2Vo(SnapshotPriceChangeDto dto) {
        SnapshotPriceChangeResponse.PriceChangeResponse response = new SnapshotPriceChangeResponse.PriceChangeResponse();
        response.setMarket(dto.getMarket());
        response.setCode(dto.getCode());
        response.setName(dto.getName());
        response.setUpdateTime(dto.getUpdateTime().format(DATE_TIME_FORMATTER));
        response.setPriceChange(dto.getPriceChange());
        return response;
    }
}
