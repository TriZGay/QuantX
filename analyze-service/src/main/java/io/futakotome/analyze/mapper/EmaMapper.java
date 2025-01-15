package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.EmaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class EmaMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmaMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EmaMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insertBatch(String toTable, List<EmaDto> emas) {
        try {
            String sql = "insert into " + toTable
                    + "(market,code,rehab_type,ema_5,ema_10,ema_20,ema_30,ema_60,ema_120,update_time) values(:market,:code,:rehabType,:ema_5,:ema_10,:ema_20,:ema_30,:ema_60,:ema_120,:updateTime)";
            int[] insertedRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(emas));
            LOGGER.info("插入成功.条数:{}", Arrays.stream(insertedRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入EMA数据失败.", e);
            return false;
        }
    }
}
