package io.futakotome.analyze.biz;

import io.futakotome.analyze.mapper.RsiMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.RsiDto;
import io.futakotome.analyze.mapper.dto.TradeDateDto;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Rsi {
    private static final Logger LOGGER = LoggerFactory.getLogger(Rsi.class);
    private final RsiMapper rsiMapper;
    private final TradeDateMapper tradeDateMapper;

    public Rsi(RsiMapper rsiMapper, TradeDateMapper tradeDateMapper) {
        this.rsiMapper = rsiMapper;
        this.tradeDateMapper = tradeDateMapper;
    }

    public void calculate(String toTableName, String fromTableName, String startDateTime, String endDateTime) {
        LocalDate startDate = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)
                .toLocalDate();
        if (startDate.isEqual(LocalDate.of(2025, 1, 2))) {
            //初始值从今年开始1月2日交易日开始算
            List<RsiDto> toAddList = rsiMapper.insertPrefetch(fromTableName, startDateTime, endDateTime);
            if (rsiMapper.insertBatch(toTableName, toAddList)) {
                LOGGER.info("{}->{}时间段:{}-{}归档RSI数据成功.", fromTableName, toTableName, startDateTime, endDateTime);
            }
        } else {
            //不是1月2日的话从前一天的开始算
            List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, startDate, "1");
            if (Objects.nonNull(tradeDates)) {
                if (!tradeDates.isEmpty()) {
                    TradeDateDto sdt = tradeDates.get(0);
                    String start = sdt.getTime().atStartOfDay().format(DateUtils.DATE_TIME_FORMATTER);
                    List<RsiDto> toAddList = rsiMapper.insertPrefetch(fromTableName, start, endDateTime);
                    if (rsiMapper.insertBatch(toTableName, toAddList)) {
                        LOGGER.info("{}->{}时间段:{}-{}归档RSI数据成功.", fromTableName, toTableName, startDateTime, endDateTime);
                    }
                } else {
                    LOGGER.error("{}->{}时间段:{}-{}归档RSI数据失败.交易日期缺失数据", fromTableName, toTableName,
                            startDateTime, endDateTime);
                }
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档RSI数据失败.交易日期为null", fromTableName, toTableName,
                        startDateTime, endDateTime);
            }
        }
    }
}
