package io.futakotome.rtck.cache;

import io.futakotome.rtck.mapper.EmaMapper;
import io.futakotome.rtck.mapper.dto.EmaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.futakotome.rtck.mapper.EmaMapper.EMA_MIN_1_ARC_TABLE;

@Component
public class EmaComputeCache implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmaComputeCache.class);
    private final EmaMapper emaMapper;
    /**
     * {
     * key: code+rehab_type
     * value:初始值,和存放上次的值给本次迭代使用
     * }
     */
    public static final Map<CodeRehabTypeKey, EmaDto> MIN_1_CACHE = new HashMap<>();

    public EmaComputeCache(EmaMapper emaMapper) {
        this.emaMapper = emaMapper;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<EmaDto> min1LatestEma = emaMapper.queryLatest(EMA_MIN_1_ARC_TABLE);
        loadCache(EMA_MIN_1_ARC_TABLE, min1LatestEma);
    }

    private void loadCache(String table, List<EmaDto> emas) {
        if (EMA_MIN_1_ARC_TABLE.equals(table)) {
            emas.forEach(ema -> MIN_1_CACHE.put(new CodeRehabTypeKey(ema.getCode(), ema.getRehabType()), ema));
            LOGGER.info("1分钟级ema计算缓存读取成功:{}", MIN_1_CACHE);
        }
    }
}
