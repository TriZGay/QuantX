package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.EmaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class EmaMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmaMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public static final String EMA_MIN_1_TABLE_NAME = "t_ema_min_1_arc";

    public EmaMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<EmaDto> queryList(EmaDto emaDto) {
        try {
            String sql = "select market,code,rehab_type,round(ema_5,4) as ema_5,round(ema_10,4) as ema_10,round(ema_20,4) as ema_20," +
                    "round(ema_60,4) as ema_60,round(ema_120,4) as ema_120,update_time from :table " +
                    "prewhere (1=1) ";
            if (Objects.nonNull(emaDto.getCode())) {
                sql += " and code=:code";
            }
            if (Objects.nonNull(emaDto.getRehabType())) {
                sql += " and rehab_type=:rehabType";
            }
            if (Objects.nonNull(emaDto.getStart())) {
                sql += " and update_time>=:start";
            }
            if (Objects.nonNull(emaDto.getEnd())) {
                sql += " and update_time<=:end";
            }
            sql += " order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(emaDto),
                    new BeanPropertyRowMapper<>(EmaDto.class));
        } catch (Exception e) {
            LOGGER.error("查询EMA数据失败.", e);
            return null;
        }
    }

    public boolean insertBatch(String toTable, List<EmaDto> emas) {
        try {
            String sql = "insert into " + toTable
                    + "(market,code,rehab_type,close_price,ema_5,ema_10,ema_12,ema_20,ema_26,ema_60,ema_120,update_time) values(:market,:code,:rehabType,:closePrice,:ema_5,:ema_10,:ema_12,:ema_20,:ema_26,:ema_60,:ema_120,:updateTime)";
            int[] insertedRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(emas));
            LOGGER.info("插入成功.条数:{}", Arrays.stream(insertedRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入EMA数据失败.", e);
            return false;
        }
    }
}
