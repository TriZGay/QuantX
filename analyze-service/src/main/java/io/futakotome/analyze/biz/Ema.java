package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.EmaRequest;
import io.futakotome.analyze.controller.vo.EmaResponse;
import io.futakotome.analyze.mapper.EmaMapper;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.CodeAndRehabTypeKey;
import io.futakotome.analyze.mapper.dto.EmaDto;
import io.futakotome.analyze.mapper.dto.KLineDto;
import io.futakotome.analyze.mapper.dto.TradeDateDto;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ema {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ema.class);
    private EmaMapper emaMapper;
    private KLineMapper kLineMapper;
    private TradeDateMapper tradeDateMapper;

    public Ema() {
    }

    public Ema(EmaMapper emaMapper) {
        this.emaMapper = emaMapper;
    }

    public Ema(EmaMapper emaMapper, KLineMapper kLineMapper, TradeDateMapper tradeDateMapper) {
        this.emaMapper = emaMapper;
        this.kLineMapper = kLineMapper;
        this.tradeDateMapper = tradeDateMapper;
    }

    public List<EmaResponse> list(EmaRequest emaRequest) {
        switch (emaRequest.getGranularity()) {
            case 1:
                //1分k
                return emaMapper.queryList(new EmaDto(EmaMapper.EMA_MIN_1_TABLE_NAME, emaRequest.getCode(), emaRequest.getRehabType(),
                                emaRequest.getStart(), emaRequest.getEnd()))
                        .stream().map(this::dto2Vo)
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }

    public void calculate(String toTable, String kTable, String startDateTime, String endDateTime) {
        LocalDate startDate = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER).toLocalDate();
        if (startDate.isEqual(LocalDate.of(2025, 1, 2))) {
            //ema初始值从今年开始1月2日交易日开始算
            List<KLineDto> kLineDtos = kLineMapper.queryKLineArchived(new KLineDto(kTable, startDateTime, endDateTime));
            Map<CodeAndRehabTypeKey, List<KLineDto>> groupingKLineByCodeAndRehabType = kLineDtos.stream()
                    .collect(Collectors.groupingBy(k -> new CodeAndRehabTypeKey(k.getRehabType(), k.getCode())));
            groupingKLineByCodeAndRehabType.keySet().forEach(key -> {
                List<KLineDto> kGroupByKey = groupingKLineByCodeAndRehabType.get(key);
                List<EmaDto> insertDtos = new ArrayList<>();
                EmaInternal ema5 = new EmaInternal(5);
                EmaInternal ema10 = new EmaInternal(10);
                EmaInternal ema12 = new EmaInternal(12);
                EmaInternal ema20 = new EmaInternal(20);
                EmaInternal ema26 = new EmaInternal(26);
                EmaInternal ema60 = new EmaInternal(60);
                EmaInternal ema120 = new EmaInternal(120);
                kGroupByKey.forEach(kLineDto -> {
                    EmaDto insertDto = new EmaDto();
                    Double ema5Val = ema5.calculate(kLineDto.getClosePrice());
                    Double ema10Val = ema10.calculate(kLineDto.getClosePrice());
                    Double ema12Val = ema12.calculate(kLineDto.getClosePrice());
                    Double ema20Val = ema20.calculate(kLineDto.getClosePrice());
                    Double ema26Val = ema26.calculate(kLineDto.getClosePrice());
                    Double ema60Val = ema60.calculate(kLineDto.getClosePrice());
                    Double ema120Val = ema120.calculate(kLineDto.getClosePrice());
                    insertDto.setMarket(kLineDto.getMarket());
                    insertDto.setRehabType(kLineDto.getRehabType());
                    insertDto.setCode(kLineDto.getCode());
                    insertDto.setEma_5(ema5Val);
                    insertDto.setEma_10(ema10Val);
                    insertDto.setEma_12(ema12Val);
                    insertDto.setEma_20(ema20Val);
                    insertDto.setEma_26(ema26Val);
                    insertDto.setEma_60(ema60Val);
                    insertDto.setEma_120(ema120Val);
                    insertDto.setUpdateTime(kLineDto.getUpdateTime());
                    insertDtos.add(insertDto);
                });
                if (emaMapper.insertBatch(toTable, insertDtos)) {
                    LOGGER.info("{}->{}时间段:{}-{}归档EMA数据成功.", kTable, toTable, startDateTime, endDateTime);
                }
            });
        } else {
            //不是1月2日的话从前一天的ema值开始算
            List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, startDate, "1");
            if (Objects.nonNull(tradeDates)) {
                if (!tradeDates.isEmpty()) {
                    TradeDateDto sdt = tradeDates.get(0);
                    List<KLineDto> kLineDtos = kLineMapper.queryKLineArchived(new KLineDto(kTable, startDateTime, endDateTime));
                    Map<CodeAndRehabTypeKey, List<KLineDto>> groupingKLineByCodeAndRehabType = kLineDtos.stream()
                            .collect(Collectors.groupingBy(k -> new CodeAndRehabTypeKey(k.getRehabType(), k.getCode())));
                    for (CodeAndRehabTypeKey key : groupingKLineByCodeAndRehabType.keySet()) {
                        List<KLineDto> kGroupByKey = groupingKLineByCodeAndRehabType.get(key);
                        List<EmaDto> forwardEmas = emaMapper.queryList(new EmaDto(toTable, key.getCode(), key.getRehabType(), sdt.getTime().atStartOfDay().format(DateUtils.DATE_TIME_FORMATTER), endDateTime));
                        EmaDto initialEma = forwardEmas.stream().filter(forwardEma ->
                                        LocalDateTime.parse(forwardEma.getUpdateTime(), DateUtils.DATE_TIME_FORMATTER).isBefore(LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)))
                                .findFirst().orElseGet(() -> {
                                    //有些标的物不是从2025年1月2号开始收集K线数据
                                    EmaDto initEma = new EmaDto();
                                    initEma.setEma_5(kGroupByKey.get(0).getClosePrice());
                                    initEma.setEma_10(kGroupByKey.get(0).getClosePrice());
                                    initEma.setEma_12(kGroupByKey.get(0).getClosePrice());
                                    initEma.setEma_20(kGroupByKey.get(0).getClosePrice());
                                    initEma.setEma_26(kGroupByKey.get(0).getClosePrice());
                                    initEma.setEma_60(kGroupByKey.get(0).getClosePrice());
                                    initEma.setEma_120(kGroupByKey.get(0).getClosePrice());
                                    return initEma;
                                });
                        List<EmaDto> insertDtos = new ArrayList<>();
                        EmaInternal ema5 = new EmaInternal(5, initialEma.getEma_5());
                        EmaInternal ema10 = new EmaInternal(10, initialEma.getEma_10());
                        EmaInternal ema12 = new EmaInternal(12, initialEma.getEma_12());
                        EmaInternal ema20 = new EmaInternal(20, initialEma.getEma_20());
                        EmaInternal ema26 = new EmaInternal(26, initialEma.getEma_26());
                        EmaInternal ema60 = new EmaInternal(60, initialEma.getEma_60());
                        EmaInternal ema120 = new EmaInternal(120, initialEma.getEma_120());
                        kGroupByKey.forEach(kLineDto -> {
                            EmaDto insertDto = new EmaDto();
                            Double ema5Val = ema5.calculate(kLineDto.getClosePrice());
                            Double ema10Val = ema10.calculate(kLineDto.getClosePrice());
                            Double ema12Val = ema12.calculate(kLineDto.getClosePrice());
                            Double ema20Val = ema20.calculate(kLineDto.getClosePrice());
                            Double ema26Val = ema26.calculate(kLineDto.getClosePrice());
                            Double ema60Val = ema60.calculate(kLineDto.getClosePrice());
                            Double ema120Val = ema120.calculate(kLineDto.getClosePrice());
                            insertDto.setMarket(kLineDto.getMarket());
                            insertDto.setRehabType(kLineDto.getRehabType());
                            insertDto.setCode(kLineDto.getCode());
                            insertDto.setEma_5(ema5Val);
                            insertDto.setEma_10(ema10Val);
                            insertDto.setEma_12(ema12Val);
                            insertDto.setEma_20(ema20Val);
                            insertDto.setEma_26(ema26Val);
                            insertDto.setEma_60(ema60Val);
                            insertDto.setEma_120(ema120Val);
                            insertDto.setUpdateTime(kLineDto.getUpdateTime());
                            insertDtos.add(insertDto);
                        });
                        if (emaMapper.insertBatch(toTable, insertDtos)) {
                            LOGGER.info("{}->{}时间段:{}-{}归档EMA数据成功.", kTable, toTable, startDateTime, endDateTime);
                        }
                    }
                } else {
                    LOGGER.error("{}->{}时间段:{}-{}归档EMA数据失败.交易日期缺失数据", kTable, toTable,
                            startDateTime, endDateTime);
                }
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档EMA数据失败.交易日期为null", kTable, toTable,
                        startDateTime, endDateTime);
            }

        }
    }

    private EmaResponse dto2Vo(EmaDto dto) {
        EmaResponse response = new EmaResponse();
        response.setMarket(dto.getMarket());
        response.setRehabType(dto.getRehabType());
        response.setCode(dto.getCode());
        response.setEma5Value(dto.getEma_5());
        response.setEma10Value(dto.getEma_10());
        response.setEma20Value(dto.getEma_20());
        response.setEma60Value(dto.getEma_60());
        response.setEma120Value(dto.getEma_120());
        response.setUpdateTime(dto.getUpdateTime());
        return response;

    }

    public static class EmaInternal {
        private final Double multiplier;
        private Double previousEma;

        public EmaInternal(Integer period) {
            this.multiplier = 2.0 / (period + 1);
            this.previousEma = null;
        }

        public EmaInternal(Integer period, Double initial) {
            this.multiplier = 2.0 / (period + 1);
            this.previousEma = initial;
        }

        //可指定初值或不指定初值
        public Double calculate(Double price) {
            if (previousEma == null) {
                previousEma = price;
            } else {
                previousEma = ((price - previousEma) * multiplier) + previousEma;
            }
            return previousEma;
        }
    }

}
