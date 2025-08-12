package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.KdjDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class KdjMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(KdjMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public static final String KDJ_MIN_1_TABLE_NAME = "t_kdj_min_1_arc";

    public KdjMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<KdjDto> queryList(KdjDto kdjDto) {
        try {
            String sql = "select market,code,rehab_type,round(k,4) as k,round(d,4)as d,round(j,4)as j,toString(update_time) as update_time" +
                    " from :table" +
                    " prewhere (1=1)";
            if (Objects.nonNull(kdjDto.getCode())) {
                sql += " and (code = :code)";
            }
            if (Objects.nonNull(kdjDto.getRehabType())) {
                sql += " and (rehab_type = :rehabType)";
            }
            if (Objects.nonNull(kdjDto.getStart())) {
                sql += " and (update_time >= :start) ";
            }
            if (Objects.nonNull(kdjDto.getEnd())) {
                sql += " and (update_time <= :end) ";
            }
            sql += "order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(kdjDto),
                    new BeanPropertyRowMapper<>(KdjDto.class));
        } catch (Exception e) {
            LOGGER.error("查询kdj数据失败.", e);
            return null;
        }
    }

    public boolean insertBatch(String toTable, List<KdjDto> kdjDtos) {
        try {
            String sql = "insert into " + toTable +
                    "(market, code, rehab_type, k, d, j, update_time)" +
                    "values (:market,:code,:rehabType,:k,:d,:j,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(kdjDtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入kdj数据失败.", e);
            return false;
        }
    }

    public List<KdjDto> queryRsv(String fromTable, String startDateTime, String endDateTime) {
        try {
            String sql = "with kdj_base as" +
                    "         (select market," +
                    "                 code," +
                    "                 rehab_type," +
                    "                 toString(update_time) as update_time," +
                    "                 close_price," +
                    "                 max(high_price)" +
                    "                     over (partition by (code, rehab_type) order by update_time  rows BETWEEN 8 preceding and current row)  as highest_9," +
                    "                 min(low_price)" +
                    "                     over (partition by (code, rehab_type) order by update_time  rows BETWEEN 8 preceding and current row) as lowest_9" +
                    "          from :fromTable " +
                    "          where update_time >= :start " +
                    "            and update_time <= :end), " +
                    "     rsv_data as" +
                    "         (select *," +
                    "                 case" +
                    "                     when highest_9 = lowest_9 then 50" +
                    "                     else (close_price - lowest_9) / (highest_9 - lowest_9) * 100" +
                    "                     end as rsv" +
                    "          from kdj_base)" +
                    "select market, code, rehab_type, update_time, highest_9, lowest_9, rsv " +
                    "from rsv_data;";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("fromTable", fromTable);
                put("start", startDateTime);
                put("end", endDateTime);
            }}, new BeanPropertyRowMapper<>(KdjDto.class));
        } catch (Exception e) {
            LOGGER.error("预查询rsv数据失败.", e);
            return null;
        }
    }
}
