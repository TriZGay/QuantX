package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.ArbrRequest;
import io.futakotome.analyze.controller.vo.ArbrResponse;
import io.futakotome.analyze.mapper.ArbrMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.ArbrDto;
import io.futakotome.analyze.mapper.dto.TradeDateDto;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Arbr {
    private static final Logger LOGGER = LoggerFactory.getLogger(Arbr.class);
    private final ArbrMapper arbrMapper;
    private TradeDateMapper tradeDateMapper;

    public Arbr(ArbrMapper arbrMapper) {
        this.arbrMapper = arbrMapper;
    }

    public Arbr(ArbrMapper arbrMapper, TradeDateMapper tradeDateMapper) {
        this.arbrMapper = arbrMapper;
        this.tradeDateMapper = tradeDateMapper;
    }

    public List<ArbrResponse> list(ArbrRequest arbrRequest) {
        switch (arbrRequest.getGranularity()) {
            case 1:
                //1分k
                return arbrMapper.queryArbrList(new ArbrDto(ArbrMapper.ARBR_MIN_1_TABLE_NAME, arbrRequest.getCode(), arbrRequest.getRehabType(), arbrRequest.getStart(), arbrRequest.getEnd()))
                        .stream().map(this::dto2Vo)
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }

    private ArbrResponse dto2Vo(ArbrDto arbrDto) {
        ArbrResponse arbrResponse = new ArbrResponse();
        arbrResponse.setMarket(arbrDto.getMarket());
        arbrResponse.setCode(arbrDto.getCode());
        arbrResponse.setRehabType(arbrDto.getRehabType());
        arbrResponse.setAr(arbrDto.getAr());
        arbrResponse.setBr(arbrDto.getBr());
        arbrResponse.setUpdateTime(arbrDto.getUpdateTime());
        return arbrResponse;
    }

    public void calculate(String toTableName, String fromTableName, String startDateTime, String endDateTime) {
        LocalDate startDate = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)
                .toLocalDate();
        List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, startDate, "1");
        if (Objects.nonNull(tradeDates)) {
            if (!tradeDates.isEmpty()) {
                TradeDateDto sdt = tradeDates.get(0);
                String start = sdt.getTime().atStartOfDay().format(DateUtils.DATE_TIME_FORMATTER);
                List<ArbrDto> prefetchDto = arbrMapper.insertPrefetch(fromTableName, start, endDateTime);
                List<ArbrDto> toAddList = prefetchDto.stream()
                        .filter(arbrDto -> {
                            LocalDateTime dataDateTime = LocalDateTime.parse(arbrDto.getUpdateTime(), DateUtils.DATE_TIME_WITH_MILLISECOND_FORMATTER);
                            LocalDateTime startBoundary = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER);
                            return dataDateTime.isEqual(startBoundary) || dataDateTime.isAfter(startBoundary);
                        })
                        .collect(Collectors.toList());
                if (arbrMapper.insertBatch(toTableName, toAddList)) {
                    LOGGER.info("{}->{}时间段:{}-{}归档ARBR数据成功.", fromTableName, toTableName, startDateTime, endDateTime);
                }
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档ARBR数据失败.交易日期缺失数据", fromTableName, toTableName,
                        startDateTime, endDateTime);
            }
        } else {
            LOGGER.error("{}->{}时间段:{}-{}归档ARBR数据失败.交易日期为null", fromTableName, toTableName,
                    startDateTime, endDateTime);
        }
    }
}
