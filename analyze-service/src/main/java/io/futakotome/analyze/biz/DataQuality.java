package io.futakotome.analyze.biz;


import io.futakotome.analyze.controller.vo.DataQualityResponse;
import io.futakotome.analyze.controller.vo.KLineRepeatResponse;
import io.futakotome.analyze.controller.vo.RangeRequest;
import io.futakotome.analyze.mapper.DataQualityMapper;
import io.futakotome.analyze.mapper.dto.DataQualityDto;
import io.futakotome.analyze.mapper.dto.KLineRepeatDetailDto;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataQuality {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataQuality.class);
    private final DataQualityMapper repository;
    private KLine kLine;

    public DataQuality(DataQualityMapper repository) {
        this.repository = repository;
    }

    public DataQuality(DataQualityMapper mapper, KLine kLine) {
        this.repository = mapper;
        this.kLine = kLine;
    }

    public Map<String, Boolean> qaPerDay(RangeRequest request) {
        List<DataQualityDto> dataQualityDtos = repository.list(
                LocalDate.parse(request.getStart(), DateUtils.DATE_FORMATTER),
                LocalDate.parse(request.getEnd(), DateUtils.DATE_FORMATTER)
        );
        if (Objects.nonNull(dataQualityDtos)) {
            Map<String, Boolean> dataQaMap = new HashMap<>();
            dataQualityDtos.stream()
                    .map(this::dataQaDto2Vo)
                    .forEach(vo -> dataQaMap.put(vo.getCheckDate(), vo.isKlineHasRepeat()));
            return dataQaMap;
        } else {
            throw new RuntimeException("数据质量查询失败");
        }
    }

    public void checkKlineRepeat(LocalDate now, String tableName) {
        String todayMin = LocalDateTime.of(now, LocalTime.MIN).format(DateUtils.DATE_TIME_FORMATTER);
        String todayMax = LocalDateTime.of(now, LocalTime.MAX).format(DateUtils.DATE_TIME_FORMATTER);
        LOGGER.info("检查时段:{}-{}", todayMin, todayMax);
        List<KLineRepeatResponse> kLineRepeatResponses = kLine.kLinesRepeat(todayMin, todayMax, tableName);
        DataQualityDto dataQualityDto = repository.queryOneBy(now);
        if (!kLineRepeatResponses.isEmpty()) {
            //有重复数据
            if (Objects.nonNull(dataQualityDto)) {
                //存在则更新
                if (repository.updateKlineRepeatStatus(now, true)) {
                    LOGGER.info("日期:{},K线数据有重复,更新成功.", now.format(DateUtils.DATE_FORMATTER));
                }
            } else {
                if (repository.insertOne(new DataQualityDto(now, true))) {
                    LOGGER.info("日期:{},K线数据有重复,新增成功.", now.format(DateUtils.DATE_FORMATTER));
                }
            }
            if (repository.insertRepeatDetails(kLineRepeatResponses.stream()
                    .map(vo -> klineRepeatVo2Dto(now, vo))
                    .collect(Collectors.toList()))) {
                LOGGER.info("日期:{},K线重复细节插入成功.", now.format(DateUtils.DATE_FORMATTER));
            }
        } else {
            if (Objects.nonNull(dataQualityDto)) {
                if (repository.updateKlineRepeatStatus(now, false)) {
                    LOGGER.info("日期:{},K线数据无重复,更新成功.", now.format(DateUtils.DATE_FORMATTER));
                }
            } else {
                if (repository.insertOne(new DataQualityDto(now, false))) {
                    LOGGER.info("日期:{},K线数据无重复,新增成功.", now.format(DateUtils.DATE_FORMATTER));
                }
            }
        }
    }

    private DataQualityResponse dataQaDto2Vo(DataQualityDto dto) {
        DataQualityResponse response = new DataQualityResponse();
        response.setCheckDate(dto.getCheckDate().format(DateUtils.DATE_FORMATTER));
        response.setKlineHasRepeat(dto.isKlineHasRepeat());
        return response;
    }

    private KLineRepeatDetailDto klineRepeatVo2Dto(LocalDate checkDate, KLineRepeatResponse response) {
        KLineRepeatDetailDto dto = new KLineRepeatDetailDto();
        dto.setCode(response.getCode());
        dto.setCheckDate(checkDate);
        dto.setRehabType(response.getRehabType());
        dto.setUpdateTime(response.getUpdateTime());
        return dto;
    }

}
