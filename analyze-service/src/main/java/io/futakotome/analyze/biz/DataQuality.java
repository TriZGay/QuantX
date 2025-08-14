package io.futakotome.analyze.biz;


import io.futakotome.analyze.controller.vo.*;
import io.futakotome.analyze.mapper.DataQualityMapper;
import io.futakotome.analyze.mapper.dto.DataQualityDto;
import io.futakotome.analyze.mapper.dto.KLineRepeatDetailDto;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataQuality {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataQuality.class);
    private final DataQualityMapper repository;
    private final KLine kLine;

    public DataQuality(DataQualityMapper repository, KLine kLine) {
        this.repository = repository;
        this.kLine = kLine;
    }

    public DataQaDetailsResponse details(DataQaDetailsRequest request) {
        List<KLineRepeatDetailDto> repeatDetailDtos = repository.queryRepeatDetailsBy(LocalDate.parse(request.getDate(),
                DateUtils.DATE_FORMATTER));
        DataQaDetailsResponse response = new DataQaDetailsResponse();
        if (Objects.nonNull(repeatDetailDtos)) {
            List<DataQaDetailsResponse.RepeatDetail> repeatDetails = repeatDetailDtos
                    .stream().map(this::repeatDetailDto2Vo).collect(Collectors.toList());
            response.setRepeatDetails(repeatDetails);
        } else {
            response.setRepeatDetails(new ArrayList<>());
        }
        return response;
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
            List<KLineRepeatDetailDto> dtos = kLineRepeatResponses.stream()
                    .map(vo -> klineRepeatVo2Dto(now, tableName, vo))
                    .collect(Collectors.toList());
            if (repository.insertRepeatDetails(dtos)) {
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

    private DataQaDetailsResponse.RepeatDetail repeatDetailDto2Vo(KLineRepeatDetailDto dto) {
        DataQaDetailsResponse.RepeatDetail repeatDetail = new DataQaDetailsResponse.RepeatDetail();
        repeatDetail.setCheckDate(dto.getCheckDate().format(DateUtils.DATE_FORMATTER));
        repeatDetail.setCode(dto.getCode());
        repeatDetail.setRehabType(dto.getRehabType());
        repeatDetail.setUpdateTime(dto.getUpdateTime());
        repeatDetail.setTableName(dto.getTableName());
        return repeatDetail;
    }

    private DataQualityResponse dataQaDto2Vo(DataQualityDto dto) {
        DataQualityResponse response = new DataQualityResponse();
        response.setCheckDate(dto.getCheckDate().format(DateUtils.DATE_FORMATTER));
        response.setKlineHasRepeat(dto.isKlineHasRepeat());
        return response;
    }

    private KLineRepeatDetailDto klineRepeatVo2Dto(LocalDate checkDate, String tableName, KLineRepeatResponse response) {
        KLineRepeatDetailDto dto = new KLineRepeatDetailDto();
        dto.setCode(response.getCode());
        dto.setRehabType(response.getRehabType());
        dto.setUpdateTime(response.getUpdateTime());
        dto.setCheckDate(checkDate);
        dto.setTableName(tableName);
        return dto;
    }

}
