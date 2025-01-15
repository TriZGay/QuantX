package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.EmaRequest;
import io.futakotome.analyze.controller.vo.EmaResponse;
import io.futakotome.analyze.mapper.EmaMapper;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.dto.EmaDto;
import io.futakotome.analyze.mapper.dto.KLineDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ema {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ema.class);
    private final EmaMapper emaMapper;
    private KLineMapper kLineMapper;

    public Ema(EmaMapper emaMapper) {
        this.emaMapper = emaMapper;
    }

    public Ema(EmaMapper emaMapper, KLineMapper kLineMapper) {
        this.emaMapper = emaMapper;
        this.kLineMapper = kLineMapper;
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

    public void calculate(String toTable, String fromTable, String startDateTime, String endDateTime) {
        List<KLineDto> kLineDtos = kLineMapper.queryKLineArchived(new KLineDto(fromTable, startDateTime, endDateTime));
        Map<CodeAndRehabTypeKey, List<KLineDto>> groupingKLineByCodeAndRehabType = kLineDtos.stream()
                .collect(Collectors.groupingBy(k -> new CodeAndRehabTypeKey(k.getRehabType(), k.getCode())));
        groupingKLineByCodeAndRehabType.keySet().forEach(key -> {
            List<KLineDto> maGroupByKey = groupingKLineByCodeAndRehabType.get(key);
            List<EmaDto> insertDtos = new ArrayList<>();
            EmaInternal ema5 = new EmaInternal(5);
            EmaInternal ema10 = new EmaInternal(10);
            EmaInternal ema20 = new EmaInternal(20);
            EmaInternal ema30 = new EmaInternal(30);
            EmaInternal ema60 = new EmaInternal(60);
            EmaInternal ema120 = new EmaInternal(120);
            maGroupByKey.forEach(kLineDto -> {
                EmaDto insertDto = new EmaDto();
                Double ema5Val = ema5.calculate(kLineDto.getClosePrice());
                Double ema10Val = ema10.calculate(kLineDto.getClosePrice());
                Double ema20Val = ema20.calculate(kLineDto.getClosePrice());
                Double ema30Val = ema30.calculate(kLineDto.getClosePrice());
                Double ema60Val = ema60.calculate(kLineDto.getClosePrice());
                Double ema120Val = ema120.calculate(kLineDto.getClosePrice());
                insertDto.setMarket(kLineDto.getMarket());
                insertDto.setRehabType(kLineDto.getRehabType());
                insertDto.setCode(kLineDto.getCode());
                insertDto.setEma_5(ema5Val);
                insertDto.setEma_10(ema10Val);
                insertDto.setEma_20(ema20Val);
                insertDto.setEma_30(ema30Val);
                insertDto.setEma_60(ema60Val);
                insertDto.setEma_120(ema120Val);
                insertDto.setUpdateTime(kLineDto.getUpdateTime());
                insertDtos.add(insertDto);
            });
            if (emaMapper.insertBatch(toTable, insertDtos)) {
                LOGGER.info("{}->{}时间段:{}-{}归档EMA数据成功.", fromTable, toTable, startDateTime, endDateTime);
            }
        });
    }

    private EmaResponse dto2Vo(EmaDto dto) {
        EmaResponse response = new EmaResponse();
        response.setMarket(dto.getMarket());
        response.setRehabType(dto.getRehabType());
        response.setCode(dto.getCode());
        response.setEma5Value(dto.getEma_5());
        response.setEma10Value(dto.getEma_10());
        response.setEma20Value(dto.getEma_20());
        response.setEma30Value(dto.getEma_30());
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

        public Double calculate(Double price) {
            if (previousEma == null) {
                previousEma = price;
            } else {
                previousEma = ((price - previousEma) * multiplier) + previousEma;
            }
            return previousEma;
        }
    }

    public static class CodeAndRehabTypeKey {
        private Integer rehabType;
        private String code;

        public CodeAndRehabTypeKey(Integer rehabType, String code) {
            this.rehabType = rehabType;
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CodeAndRehabTypeKey that = (CodeAndRehabTypeKey) o;
            return Objects.equals(rehabType, that.rehabType) && Objects.equals(code, that.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rehabType, code);
        }

        public Integer getRehabType() {
            return rehabType;
        }

        public void setRehabType(Integer rehabType) {
            this.rehabType = rehabType;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
