package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.EmaRequest;
import io.futakotome.analyze.controller.vo.EmaResponse;
import io.futakotome.analyze.mapper.EmaMapper;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MaNMapper;
import io.futakotome.analyze.mapper.dto.CodeAndRehabTypeKey;
import io.futakotome.analyze.mapper.dto.EmaDto;
import io.futakotome.analyze.mapper.dto.KLineDto;
import io.futakotome.analyze.mapper.dto.MaNDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ema {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ema.class);
    private EmaMapper emaMapper;
    private KLineMapper kLineMapper;
    private MaNMapper maNMapper;

    public Ema() {
    }

    public Ema(EmaMapper emaMapper) {
        this.emaMapper = emaMapper;
    }

    public Ema(EmaMapper emaMapper, KLineMapper kLineMapper, MaNMapper maNMapper) {
        this.emaMapper = emaMapper;
        this.kLineMapper = kLineMapper;
        this.maNMapper = maNMapper;
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

    public void calculate(String toTable, String kTable, String maTable, String startDateTime, String endDateTime) {
        List<KLineDto> kLineDtos = kLineMapper.queryKLineArchived(new KLineDto(kTable, startDateTime, endDateTime));
        Map<CodeAndRehabTypeKey, List<KLineDto>> groupingKLineByCodeAndRehabType = kLineDtos.stream()
                .collect(Collectors.groupingBy(k -> new CodeAndRehabTypeKey(k.getRehabType(), k.getCode())));
        groupingKLineByCodeAndRehabType.keySet().forEach(key -> {
            List<KLineDto> kGroupByKey = groupingKLineByCodeAndRehabType.get(key);
            List<MaNDto> maNs = maNMapper.queryMaN(new MaNDto(maTable, key.getCode(), key.getRehabType(), startDateTime, startDateTime));
            List<EmaDto> insertDtos = new ArrayList<>();
            EmaInternal ema5 = new EmaInternal(5, maNs.get(0).getMa_5());
            EmaInternal ema10 = new EmaInternal(10, maNs.get(0).getMa_10());
            EmaInternal ema20 = new EmaInternal(20, maNs.get(0).getMa_20());
            EmaInternal ema30 = new EmaInternal(30, maNs.get(0).getMa_30());
            EmaInternal ema60 = new EmaInternal(60, maNs.get(0).getMa_60());
            EmaInternal ema120 = new EmaInternal(120, maNs.get(0).getMa_120());
            kGroupByKey.forEach(kLineDto -> {
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
                LOGGER.info("{}&{}->{}时间段:{}-{}归档EMA数据成功.", kTable, maTable, toTable, startDateTime, endDateTime);
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
