package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.KdjRequest;
import io.futakotome.analyze.controller.vo.KdjResponse;
import io.futakotome.analyze.mapper.KdjMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.CodeAndRehabTypeKey;
import io.futakotome.analyze.mapper.dto.KdjDto;
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

public class Kdj {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rsi.class);
    private final KdjMapper kdjMapper;
    private TradeDateMapper tradeDateMapper;

    public Kdj(KdjMapper kdjMapper, TradeDateMapper tradeDateMapper) {
        this.kdjMapper = kdjMapper;
        this.tradeDateMapper = tradeDateMapper;
    }

    public Kdj(KdjMapper kdjMapper) {
        this.kdjMapper = kdjMapper;
    }

    public List<KdjResponse> queryKdjList(KdjRequest kdjRequest) {
        switch (kdjRequest.getGranularity()) {
            case 1:
                return kdjMapper.queryList(new KdjDto(KdjMapper.KDJ_MIN_1_TABLE_NAME, kdjRequest.getCode(), kdjRequest.getRehabType(), kdjRequest.getStart(), kdjRequest.getEnd()))
                        .stream().map(this::dto2Vo).collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }

    private KdjResponse dto2Vo(KdjDto kdjDto) {
        KdjResponse kdjResponse = new KdjResponse();
        kdjResponse.setMarket(kdjDto.getMarket());
        kdjResponse.setCode(kdjDto.getCode());
        kdjResponse.setRehabType(kdjDto.getRehabType());
        kdjResponse.setK(kdjDto.getK());
        kdjResponse.setD(kdjDto.getD());
        kdjResponse.setJ(kdjDto.getJ());
        kdjResponse.setUpdateTime(kdjDto.getUpdateTime());
        return kdjResponse;
    }

    public void calculate(String toTableName, String fromTableName, String startDateTime, String endDateTime) {
        LocalDate startDate = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)
                .toLocalDate();
        if (startDate.isEqual(LocalDate.of(2025, 1, 2))) {
            //初始值从今年开始1月2日交易日开始算
            List<KdjDto> rsv = kdjMapper.queryRsv(fromTableName, startDateTime, endDateTime);
            Map<CodeAndRehabTypeKey, List<KdjDto>> groupingRsvByCodeAndRehabType = rsv.stream()
                    .collect(Collectors.groupingBy(rsvValue -> new CodeAndRehabTypeKey(rsvValue.getRehabType(), rsvValue.getCode())));
            groupingRsvByCodeAndRehabType.keySet().forEach(key -> {
                List<KdjDto> kdjsByKey = groupingRsvByCodeAndRehabType.get(key);
                List<KdjDto> toAddList = rsvToKdj(kdjsByKey, null, null);
                if (kdjMapper.insertBatch(toTableName, toAddList)) {
                    LOGGER.info("{}->{}时间段:{}-{}归档KDJ数据成功.", fromTableName, toTableName, startDateTime, endDateTime);
                }
            });
        } else {
            //不是1月2日的话从前一天的开始算
            List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, startDate, "1");
            if (Objects.nonNull(tradeDates)) {
                if (!tradeDates.isEmpty()) {
                    TradeDateDto sdt = tradeDates.get(0);
                    String start = sdt.getTime().atStartOfDay().format(DateUtils.DATE_TIME_FORMATTER);
                    List<KdjDto> rsv = kdjMapper.queryRsv(fromTableName, start, endDateTime);
                    Map<CodeAndRehabTypeKey, List<KdjDto>> groupingRsvByCodeAndRehabType = rsv.stream()
                            .collect(Collectors.groupingBy(rsvValue -> new CodeAndRehabTypeKey(rsvValue.getRehabType(), rsvValue.getCode())));
                    groupingRsvByCodeAndRehabType.keySet().forEach(key -> {
                        List<KdjDto> kdjsByKey = groupingRsvByCodeAndRehabType.get(key);
                        KdjDto initKdj = kdjsByKey.stream().filter(forwardKdj ->
                                        LocalDateTime.parse(forwardKdj.getUpdateTime(), DateUtils.DATE_TIME_WITH_MILLISECOND_FORMATTER).isBefore(LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)))
                                .findFirst().orElseGet(() -> {
                                    KdjDto initKdjDto = new KdjDto();
                                    initKdjDto.setK(50.0);
                                    initKdjDto.setD(50.0);
                                    return initKdjDto;
                                });
                        List<KdjDto> toAddList = rsvToKdj(kdjsByKey, initKdj.getK(), initKdj.getD());
                        if (kdjMapper.insertBatch(toTableName, toAddList)) {
                            LOGGER.info("{}->{}时间段:{}-{}归档KDJ数据成功.", fromTableName, toTableName, startDateTime, endDateTime);
                        }
                    });
                } else {
                    LOGGER.error("{}->{}时间段:{}-{}归档KDJ数据失败.交易日期缺失数据", fromTableName, toTableName,
                            startDateTime, endDateTime);
                }
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档KDJ数据失败.交易日期为null", fromTableName, toTableName,
                        startDateTime, endDateTime);
            }
        }
    }

    private List<KdjDto> rsvToKdj(List<KdjDto> rsvList, Double initialK, Double initialD) {
        List<KdjDto> kdjList = new ArrayList<>();
        if (Objects.isNull(initialD) && Objects.isNull(initialK)) {
            //行业通用初始化值
            KdjDto initKdj = new KdjDto();
            KdjDto firstRsv = rsvList.get(0);
            initKdj.setRsv(firstRsv.getRsv());
            initKdj.setMarket(firstRsv.getMarket());
            initKdj.setCode(firstRsv.getCode());
            initKdj.setRehabType(firstRsv.getRehabType());
            initKdj.setK(50.0);
            initKdj.setD(50.0);
            initKdj.setJ(3 * 50.0 - 2 * 50.0);
            initKdj.setUpdateTime(firstRsv.getUpdateTime());
            kdjList.add(initKdj);
        } else {
            KdjDto initKdj = new KdjDto();
            KdjDto firstRsv = rsvList.get(0);
            initKdj.setRsv(firstRsv.getRsv());
            initKdj.setMarket(firstRsv.getMarket());
            initKdj.setCode(firstRsv.getCode());
            initKdj.setRehabType(firstRsv.getRehabType());
            initKdj.setK(initialK);
            initKdj.setD(initialD);
            initKdj.setJ(3 * initialK - 2 * initialD);
            initKdj.setUpdateTime(firstRsv.getUpdateTime());
            kdjList.add(initKdj);
        }

        for (int i = 1; i < rsvList.size(); i++) {
            KdjDto kdjDto = new KdjDto();
            KdjDto rsvThis = rsvList.get(i);
            kdjDto.setMarket(rsvThis.getMarket());
            kdjDto.setCode(rsvThis.getCode());
            kdjDto.setRehabType(rsvThis.getRehabType());
            kdjDto.setUpdateTime(rsvThis.getUpdateTime());
            kdjDto.setK(((double) 2 / 3) * kdjList.get(i - 1).getK() + ((double) 1 / 3) * rsvThis.getRsv());
            kdjDto.setD(((double) 2 / 3) * kdjList.get(i - 1).getD() + ((double) 1 / 3) * kdjDto.getK());
            kdjDto.setJ(3 * kdjDto.getK() - 2 * kdjDto.getD());
            // k[i] = (2/3) * k[i-1] + (1/3) * rsv[i]
            // d[i] = (2/3) * d[i-1] + (1/3) * k[i]
            // j[i] = 3*k[i] - 2*d[i]
            kdjList.add(kdjDto);
        }
        return kdjList;
    }
}
