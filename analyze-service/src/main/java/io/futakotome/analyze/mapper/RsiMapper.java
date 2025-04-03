package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.RsiDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Repository
public class RsiMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RsiMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RsiMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insertBatch(String toTable, List<RsiDto> rsiDtos) {
        try {
            String sql = "insert into " + toTable +
                    "(market, code, rehab_type, rsi_6, rsi_12, rsi_24, update_time)" +
                    "values (:market,:code,:rehabType,:rsi_6,:rsi_12,:rsi_24,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(rsiDtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入rsi数据失败.", e);
            return false;
        }
    }

    public List<RsiDto> insertPrefetch(String fromTable, String startDateTime, String endDateTime) {
        try {
            String sql = "with close_price_change as " +
                    "         (select market, " +
                    "                 code, " +
                    "                 rehab_type, " +
                    "                 close_price, " +
                    "                 leadInFrame(close_price, 1, close_price) " +
                    "                             over (partition by (code, rehab_type) order by update_time desc rows between unbounded preceding and unbounded following) as pre_close_price, " +
                    "                 close_price - pre_close_price                                                                                                         as delta, " +
                    "                 update_time " +
                    "          from :fromTable " +
                    "          where update_time >= :start " +
                    "            and update_time <= :end), " +
                    "     avg_changes as (select market, " +
                    "                            code, " +
                    "                            rehab_type, " +
                    "                            avg(case when delta > 0 then delta else 0 end) over w       as avg_gain_6, " +
                    "                            avg(case when delta < 0 then abs(delta) else 0 end) over w  as avg_loss_6, " +
                    "                            avg(case when delta > 0 then delta else 0 end) over w1      as avg_gain_12, " +
                    "                            avg(case when delta < 0 then abs(delta) else 0 end) over w1 as avg_loss_12, " +
                    "                            avg(case when delta > 0 then delta else 0 end) over w2      as avg_gain_24, " +
                    "                            avg(case when delta < 0 then abs(delta) else 0 end) over w2 as avg_loss_24, " +
                    "                            update_time " +
                    "                     from close_price_change " +
                    "                     window w as ( " +
                    "                             partition by (code, rehab_type) order by update_time desc " +
                    "                             rows between current row and 5 following " +
                    "                             ), " +
                    "                            w1 as ( " +
                    "                                    partition by (code, rehab_type) order by update_time desc " +
                    "                                    rows between current row and 11 following " +
                    "                                    ), " +
                    "                            w2 as ( " +
                    "                                    partition by (code, rehab_type) order by update_time desc " +
                    "                                    rows between current row and 23 following " +
                    "                                    )) " +
                    "select market," +
                    "       code," +
                    "       rehab_type," +
                    "       update_time," +
                    "       100 - (100 / (1 + (avg_gain_6 / avg_loss_6)))   as rsi_6," +
                    "       100 - (100 / (1 + (avg_gain_12 / avg_loss_12))) as rsi_12," +
                    "       100 - (100 / (1 + (avg_gain_24 / avg_loss_24))) as rsi_24 " +
                    "from avg_changes";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("fromTable", fromTable);
                put("start", startDateTime);
                put("end", endDateTime);
            }}, new BeanPropertyRowMapper<>(RsiDto.class));
        } catch (Exception e) {
            LOGGER.error("预查询rsi数据失败.", e);
            return null;
        }
    }
}
