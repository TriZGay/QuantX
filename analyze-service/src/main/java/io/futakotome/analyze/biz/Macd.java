package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.MacdRequest;
import io.futakotome.analyze.controller.vo.MacdResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MacdMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.*;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Macd {
    private static final Logger LOGGER = LoggerFactory.getLogger(Macd.class);
    private TradeDateMapper tradeDateMapper;
    private MacdMapper macdMapper;

    public Macd(MacdMapper macdMapper) {
        this.macdMapper = macdMapper;
    }

    public Macd(MacdMapper macdMapper, TradeDateMapper tradeDateMapper) {
        this.macdMapper = macdMapper;
        this.tradeDateMapper = tradeDateMapper;
    }

    public List<MacdResponse> queryMacdList(MacdRequest macdRequest) {
        switch (macdRequest.getGranularity()) {
            case 1:
                return macdMapper.queryList(new MacdDto(MacdMapper.MACD_MIN_1_TABLE_NAME, macdRequest.getCode(), macdRequest.getRehabType(), macdRequest.getStart(), macdRequest.getEnd()))
                        .stream().map(this::dto2Vo).collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }

    private MacdResponse dto2Vo(MacdDto macdDto) {
        MacdResponse response = new MacdResponse();
        response.setMarket(macdDto.getMarket());
        response.setCode(macdDto.getCode());
        response.setRehabType(macdDto.getRehabType());
        response.setDif(macdDto.getDif());
        response.setDea(macdDto.getDea());
        response.setMacd(macdDto.getMacd());
        response.setUpdateTime(macdDto.getUpdateTime());
        return response;
    }

    public void calculate(String toTable, String fromTable, String startDateTime, String endDateTime) {
        LocalDate startDate = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)
                .toLocalDate();
        if (startDate.isEqual(LocalDate.of(2025, 1, 2))) {
            //macd初始值从今年开始1月2日交易日开始算
            List<MacdDto> macdDtos = macdMapper.queryDif(new EmaDto(fromTable, startDateTime, endDateTime));
            Map<CodeAndRehabTypeKey, List<MacdDto>> groupingDifByCodeAndRehabType = macdDtos.stream()
                    .collect(Collectors.groupingBy(dif -> new CodeAndRehabTypeKey(dif.getRehabType(), dif.getCode())));
            groupingDifByCodeAndRehabType.keySet().forEach(key -> {
                List<MacdDto> difByKey = groupingDifByCodeAndRehabType.get(key);
                Ema.EmaInternal dea = new Ema.EmaInternal(9);
                List<MacdDto> insertDtos = difByKey.stream().peek(macdDto -> {
                    Double deaVal = dea.calculate(macdDto.getDif());
                    macdDto.setDea(deaVal);
                    Double macd = 2 * (macdDto.getDif() - macdDto.getDea());
                    macdDto.setMacd(macd);
                }).collect(Collectors.toList());
                if (macdMapper.insertBatch(toTable, insertDtos)) {
                    LOGGER.info("{}->{}时间段:{}-{}归档MACD数据成功.", fromTable, toTable, startDateTime, endDateTime);
                }
            });
        } else {
            //不是1月2日的话从前一天的macd值开始算
            List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, startDate, "1");
            if (Objects.nonNull(tradeDates)) {
                if (!tradeDates.isEmpty()) {
                    TradeDateDto sdt = tradeDates.get(0);
                    List<MacdDto> macdDtos = macdMapper.queryDif(new EmaDto(fromTable, sdt.getTime().atStartOfDay().format(DateUtils.DATE_TIME_FORMATTER), endDateTime));
                    Map<CodeAndRehabTypeKey, List<MacdDto>> groupingDifByCodeAndRehabType = macdDtos.stream()
                            .collect(Collectors.groupingBy(dif -> new CodeAndRehabTypeKey(dif.getRehabType(), dif.getCode())));
                    groupingDifByCodeAndRehabType.keySet().forEach(key -> {
                        List<MacdDto> difByKey = groupingDifByCodeAndRehabType.get(key);
                        MacdDto initMacd = difByKey.stream().filter(forwardDif ->
                                LocalDateTime.parse(forwardDif.getUpdateTime(), DateUtils.DATE_TIME_FORMATTER).isBefore(LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER))).findFirst().get();
                        Ema.EmaInternal dea = new Ema.EmaInternal(9, initMacd.getDea());
                        List<MacdDto> insertDtos = difByKey.stream().peek(macdDto -> {
                            Double deaVal = dea.calculate(macdDto.getDif());
                            macdDto.setDea(deaVal);
                            Double macd = 2 * (macdDto.getDif() - macdDto.getDea());
                            macdDto.setMacd(macd);
                        }).collect(Collectors.toList());
                        if (macdMapper.insertBatch(toTable, insertDtos)) {
                            LOGGER.info("{}->{}时间段:{}-{}归档MACD数据成功.", fromTable, toTable, startDateTime, endDateTime);
                        }
                    });
                } else {
                    LOGGER.error("{}->{}时间段:{}-{}归档MACD数据失败.交易日期缺失数据", fromTable, toTable,
                            startDateTime, endDateTime);
                }
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档MACD数据失败.交易日期为null", fromTable, toTable,
                        startDateTime, endDateTime);
            }
        }
    }
}
