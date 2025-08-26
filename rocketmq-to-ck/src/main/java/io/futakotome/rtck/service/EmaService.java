package io.futakotome.rtck.service;

import io.futakotome.rtck.cache.CodeRehabTypeKey;
import io.futakotome.rtck.cache.EmaComputeCache;
import io.futakotome.rtck.mapper.EmaMapper;
import io.futakotome.rtck.mapper.dto.EmaDto;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import io.futakotome.rtck.utils.AddTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.futakotome.rtck.mapper.EmaMapper.EMA_MIN_1_RAW_TABLE;

@Service
public class EmaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmaService.class);
    private final EmaMapper emaMapper;

    public EmaService(EmaMapper emaMapper) {
        this.emaMapper = emaMapper;
    }

    @Async
    public void computeAndInsertBatch(List<RTKLDto> toAddKLines, String table) {
        List<EmaDto> toAddEmaDtos = null;
        if (EMA_MIN_1_RAW_TABLE.equals(table)) {
            toAddEmaDtos = computeMin1Ema(toAddKLines);
        }
        if (Objects.nonNull(toAddEmaDtos) && !toAddEmaDtos.isEmpty()) {
            if (emaMapper.insertBatch(toAddEmaDtos, table)) {
                LOGGER.info("{},EMA线入库成功.", table);
            }
        }
    }

    public List<EmaDto> computeMin1Ema(List<RTKLDto> toAddKLines) {
        return toAddKLines.stream()
                .map(k -> {
                    CodeRehabTypeKey key = new CodeRehabTypeKey(k.getCode(), k.getRehabType());
                    //如果缓存里没有,就以close_price作为初值
                    EmaDto cacheValue = EmaComputeCache.MIN_1_CACHE.getOrDefault(key, new EmaDto(
                            k.getMarket(),
                            k.getCode(),
                            k.getRehabType(),
                            k.getClosePrice(),
                            k.getClosePrice(),
                            k.getClosePrice(),
                            k.getClosePrice(),
                            k.getClosePrice(),
                            k.getClosePrice(),
                            k.getClosePrice(),
                            k.getUpdateTime(),
                            AddTimeUtil.generateAddTime()));
                    EmaDto toAddEmaDto = new EmaDto();
                    toAddEmaDto.setMarket(k.getMarket());
                    toAddEmaDto.setCode(key.getCode());
                    toAddEmaDto.setRehabType(k.getRehabType());
                    Double ema5 = calculate(5, k.getClosePrice(), cacheValue.getEma_5());
                    toAddEmaDto.setEma_5(ema5);
                    Double ema10 = calculate(10, k.getClosePrice(), cacheValue.getEma_10());
                    toAddEmaDto.setEma_10(ema10);
                    Double ema12 = calculate(12, k.getClosePrice(), cacheValue.getEma_12());
                    toAddEmaDto.setEma_12(ema12);
                    Double ema20 = calculate(20, k.getClosePrice(), cacheValue.getEma_20());
                    toAddEmaDto.setEma_20(ema20);
                    Double ema26 = calculate(26, k.getClosePrice(), cacheValue.getEma_26());
                    toAddEmaDto.setEma_26(ema26);
                    Double ema60 = calculate(60, k.getClosePrice(), cacheValue.getEma_60());
                    toAddEmaDto.setEma_60(ema60);
                    Double ema120 = calculate(120, k.getClosePrice(), cacheValue.getEma_120());
                    toAddEmaDto.setEma_120(ema120);
                    toAddEmaDto.setUpdateTime(k.getUpdateTime());
                    toAddEmaDto.setAddTime(AddTimeUtil.generateAddTime());
                    //更新缓存值
                    cacheValue.setMarket(cacheValue.getMarket());
                    cacheValue.setCode(cacheValue.getCode());
                    cacheValue.setRehabType(cacheValue.getRehabType());
                    cacheValue.setEma_5(ema5);
                    cacheValue.setEma_10(ema10);
                    cacheValue.setEma_12(ema12);
                    cacheValue.setEma_20(ema20);
                    cacheValue.setEma_26(ema26);
                    cacheValue.setEma_60(ema60);
                    cacheValue.setEma_120(ema120);
                    cacheValue.setUpdateTime(k.getUpdateTime());
                    EmaComputeCache.MIN_1_CACHE.put(key, cacheValue);
                    return toAddEmaDto;
                }).collect(Collectors.toList());
    }

    private Double calculate(Integer period, Double price, Double previousEma) {
        double multiplier = 2.0 / (period + 1);
        return ((price - previousEma) * multiplier) + previousEma;
    }
}
