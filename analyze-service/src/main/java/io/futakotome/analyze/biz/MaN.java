package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.controller.vo.MaResponse;
import io.futakotome.analyze.controller.vo.MaSpan;
import io.futakotome.analyze.mapper.MaNMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.MaNDto;
import io.futakotome.analyze.mapper.dto.TradeDateDto;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MaN {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaN.class);
    private MaNMapper repository;
    private TradeDateMapper tradeDateMapper;

    public MaN(MaNMapper repository) {
        this.repository = repository;
    }

    public MaN(MaNMapper repository, TradeDateMapper tradeDateMapper) {
        this.repository = repository;
        this.tradeDateMapper = tradeDateMapper;
    }

    private Integer followingBySpan(Integer span) {
        if (span.equals(MaSpan.FIVE.getCode())) {
            return 4;
        } else if (span.equals(MaSpan.TEN.getCode())) {
            return 9;
        } else if (span.equals(MaSpan.TWENTY.getCode())) {
            return 19;
        } else if (span.equals(MaSpan.THIRTY.getCode())) {
            return 29;
        } else if (span.equals(MaSpan.SIXTY.getCode())) {
            return 59;
        } else if (span.equals(MaSpan.ONE_HUNDRED_TWENTY.getCode())) {
            return 119;
        }
        return 0;
    }

    public void klineTransToMa(String toTableName, String fromTableName, String startDateTime, String endDateTime) {
        //Clickhouse的开窗函数的边界值需要往后算120条数据才准确
        //因为香港市场的交易时间长,所以获取香港市场的交易时间也基本上覆盖大A
        //1分钟一条数据,2小时就120条数据,取startDateTime的前一天交易日作为开始边界日期
        LocalDate s = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER).toLocalDate();
        List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, s, "1");
        if (Objects.nonNull(tradeDates)) {
            if (!tradeDates.isEmpty()) {
                TradeDateDto sdt = tradeDates.get(0);
                List<MaNDto> maNs = repository.queryMaUseKArc(fromTableName, sdt.getTime().atStartOfDay().toString(), endDateTime);
                List<MaNDto> toInsertMaN = maNs.stream().filter(maNDto ->
                                LocalDateTime.parse(maNDto.getUpdateTime(), DateUtils.DATE_TIME_FORMATTER).isAfter(LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)))
                        .collect(Collectors.toList());
                if (repository.insetMaBatch(toTableName, toInsertMaN)) {
                    LOGGER.info("{}->{}时间段:{}-{}归档MA数据成功.", fromTableName, toTableName,
                            startDateTime, endDateTime);
                }
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档MA数据失败.交易日期缺失数据", fromTableName, toTableName,
                        startDateTime, endDateTime);
            }
        } else {
            LOGGER.error("{}->{}时间段:{}-{}归档MA数据失败.交易日期为null", fromTableName, toTableName,
                    startDateTime, endDateTime);
        }
    }

    public List<MaResponse> maNCommon(MaRequest maRequest) {
        switch (maRequest.getGranularity()) {
            case 1:
                //1分k
                return repository.queryMaN(MaNMapper.MA_MIN_1_TABLE_NAME, maRequest.getCode(), maRequest.getRehabType(), maRequest.getStart(), maRequest.getEnd())
                        .stream().map(this::maNDto2Response)
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }

    private MaResponse maNDto2Response(MaNDto dto) {
        MaResponse response = new MaResponse();
        response.setMarket(dto.getMarket());
        response.setCode(dto.getCode());
        response.setRehabType(dto.getRehabType());
        response.setMa5Value(dto.getMa_5());
        response.setMa10Value(dto.getMa_10());
        response.setMa20Value(dto.getMa_20());
        response.setMa30Value(dto.getMa_30());
        response.setMa60Value(dto.getMa_60());
        response.setMa120Value(dto.getMa_120());
        response.setUpdateTime(dto.getUpdateTime());
        return response;
    }
}
