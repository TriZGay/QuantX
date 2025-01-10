package io.futakotome.analyze.biz;

import io.futakotome.analyze.mapper.BollMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.BollDto;
import io.futakotome.analyze.mapper.dto.TradeDateDto;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Boll {
    private static final Logger LOGGER = LoggerFactory.getLogger(Boll.class);
    private final BollMapper repository;
    private final TradeDateMapper tradeDateMapper;

    public Boll(BollMapper repository, TradeDateMapper tradeDateMapper) {
        this.repository = repository;
        this.tradeDateMapper = tradeDateMapper;
    }

    public void computeFromKArc(String toTable, String fromTable, String startDateTime, String endDateTime) {
        //boll(20,0.2) - 中线取ma20
        //Clickhouse的开窗函数的边界值需要往后算20条数据才准确
        //因为香港市场的交易时间长,所以获取香港市场的交易时间也基本上覆盖大A
        //1分钟一条数据,2小时就120条数据,取startDateTime的前一天交易日作为开始边界日期
        LocalDate s = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER).toLocalDate();
        List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, s, "1");
        if (Objects.nonNull(tradeDates)) {
            if (!tradeDates.isEmpty()) {
                TradeDateDto sdt = tradeDates.get(0);
                List<BollDto> bollDtos = repository.queryBollUseKArc(fromTable, sdt.getTime().atStartOfDay().toString(), endDateTime);
                List<BollDto> toInsertBoll = bollDtos.stream().filter(bollDto ->
                                LocalDateTime.parse(bollDto.getUpdateTime(), DateUtils.DATE_TIME_FORMATTER).isAfter(LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)))
                        .collect(Collectors.toList());
                if (repository.insetBollBatch(toTable, toInsertBoll)) {
                    LOGGER.info("{}->{}时间段:{}-{}归档BOLL数据成功.", fromTable, toTable, startDateTime, endDateTime);
                }
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档BOLL数据失败.交易日期缺失数据", fromTable, toTable, startDateTime, endDateTime);
            }
        } else {
            LOGGER.error("{}->{}时间段:{}-{}归档BOLL数据失败.交易日期为null", fromTable, toTable, startDateTime, endDateTime);
        }
    }
}
