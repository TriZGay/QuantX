package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.EmaDto;
import io.futakotome.analyze.mapper.dto.MacdDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MacdMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmaMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public static final String EMA_MIN_1_TABLE_NAME = "t_macd_min_1_arc";

    public MacdMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insertBatch(String toTable, List<MacdDto> emas) {
        try {
            String sql = "insert into " + toTable
                    + "(market,code,rehab_type,dif,dea,macd,update_time) values(:market,:code,:rehabType,:dif,:dea,:macd,:updateTime)";
            int[] insertedRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(emas));
            LOGGER.info("插入成功.条数:{}", Arrays.stream(insertedRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入MACD数据失败.", e);
            return false;
        }
    }
}
