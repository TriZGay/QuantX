package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.ArbrDto;
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
public class ArbrMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArbrMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public static final String ARBR_MIN_1_TABLE_NAME = "t_arbr_min_1_arc";

    public ArbrMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<ArbrDto> queryArbrList(ArbrDto arbrDto) {
        try {
            String sql = "select market,code,rehab_type,round(ar,4) as ar,round(br,4)as br,toString(update_time) as update_time" +
                    " from :table" +
                    " prewhere (1=1)";
            if (Objects.nonNull(arbrDto.getCode())) {
                sql += " and (code = :code)";
            }
            if (Objects.nonNull(arbrDto.getRehabType())) {
                sql += " and (rehab_type = :rehabType)";
            }
            if (Objects.nonNull(arbrDto.getStart())) {
                sql += " and (update_time >= :start) ";
            }
            if (Objects.nonNull(arbrDto.getEnd())) {
                sql += " and (update_time <= :end) ";
            }
            sql += "order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(arbrDto),
                    new BeanPropertyRowMapper<>(ArbrDto.class));
        } catch (Exception e) {
            LOGGER.error("查询Arbr数据失败.", e);
            return null;
        }
    }

    public boolean insertBatch(String toTable, List<ArbrDto> arbrDtos) {
        try {
            String sql = "insert into " + toTable +
                    "(market, code, rehab_type, ar, br, update_time)" +
                    "values (:market,:code,:rehabType,:ar,:br,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(arbrDtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入arbr数据失败.", e);
            return false;
        }
    }

    public List<ArbrDto> insertPrefetch(String fromTable, String startDateTime, String endDateTime) {
        try {
            String sql = "with pre_close_prices as (select *, " +
                    "                                 lagInFrame(close_price, 1, close_price) " +
                    "                                            over (partition by (code, rehab_type) order by update_time rows between unbounded preceding and unbounded following) as pre_close_price " +
                    "                          from  :fromTable " +
                    "                          where update_time >= :start " +
                    "                            and update_time <= :end) " +
                    "select market, " +
                    "       code, " +
                    "       rehab_type, " +
                    "      toString( update_time) as update_time, " +
                    "       (sum(high_price - open_price) over w / sum(open_price - low_price) over w) * 100 as ar, " +
                    "       (sum(greatest(0, high_price - pre_close_price)) over w / sum(greatest(0, pre_close_price - low_price)) over w) * 100 as br " +
                    "from pre_close_prices " +
                    "window w as ( " +
                    "        partition by (code, rehab_type) " +
                    "        order by update_time " +
                    "        rows between 25 preceding and current row " +
                    "        )";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("fromTable", fromTable);
                put("start", startDateTime);
                put("end", endDateTime);
            }}, new BeanPropertyRowMapper<>(ArbrDto.class));
        } catch (Exception e) {
            LOGGER.error("预查询arbr数据失败.", e);
            return null;
        }
    }

}
