package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.EmaDto;
import io.futakotome.analyze.mapper.dto.MacdDto;
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
public class MacdMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MacdMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public static final String MACD_MIN_1_TABLE_NAME = "t_macd_min_1_arc";

    public MacdMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<MacdDto> queryList(MacdDto macdDto) {
        try {
            String sql = "select market,code,rehab_type,dif,round(dea,4) as dea,round(macd,4)as macd,update_time" +
                    " from :table" +
                    " prewhere (1=1)";
            if (Objects.nonNull(macdDto.getCode())) {
                sql += " and (code = :code)";
            }
            if (Objects.nonNull(macdDto.getRehabType())) {
                sql += " and (rehab_type = :rehabType)";
            }
            if (Objects.nonNull(macdDto.getStart())) {
                sql += " and (update_time >= :start) ";
            }
            if (Objects.nonNull(macdDto.getEnd())) {
                sql += " and (update_time <= :end) ";
            }
            sql += "order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(macdDto),
                    new BeanPropertyRowMapper<>(MacdDto.class));
        } catch (Exception e) {
            LOGGER.error("查询macd数据失败.", e);
            return null;
        }
    }

    public List<MacdDto> queryDif(EmaDto emaDto) {
        try {
            String sql = "select market,code,rehab_type,round(ema_12 - ema_26,4) as dif,toString(update_time) as update_time from :table" +
                    " prewhere (update_time>=:start) and (update_time<=:end) order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(emaDto),
                    new BeanPropertyRowMapper<>(MacdDto.class));
        } catch (Exception e) {
            LOGGER.error("查询MACD-Diff数据失败.", e);
            return null;
        }
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
