package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.BollDto;
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
public class BollMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BollMapper.class);
    public static final String BOLL_MIN_1_TABLE = "t_boll_min_1_arc";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BollMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<BollDto> queryBolls(String table, String code, Integer rehabType, String start, String end) {
        try {
            String sql = "select market,code,rehab_type,ma20_mid,double_upper,double_lower,one_upper,one_lower,triple_upper,triple_lower,update_time" +
                    " from :table" +
                    " prewhere code=:code and rehab_type=:rehabType and update_time >= :start and update_time <= :end";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("table", table);
                put("code", code);
                put("rehabType", rehabType);
                put("start", start);
                put("end", end);
            }}, new BeanPropertyRowMapper<>(BollDto.class));
        } catch (Exception e) {
            LOGGER.error("查询BOLL数据失败.", e);
            return null;
        }
    }

    public List<BollDto> queryBollUseKArc(String fromTable, String startDateTime, String endDateTime) {
        try {
            String sql = "select market,code,rehab_type," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as ma20_mid," +
                    "round(ma20_mid + 2*stddevPopStable(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as double_upper," +
                    "round(ma20_mid - 2*stddevPopStable(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as double_lower," +
                    "round(ma20_mid + stddevPopStable(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as one_upper," +
                    "round(ma20_mid - stddevPopStable(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as one_lower," +
                    "round(ma20_mid + 3*stddevPopStable(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as triple_upper," +
                    "round(ma20_mid - 3*stddevPopStable(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as triple_lower," +
                    "update_time" +
                    " from :table" +
                    " where update_time>=:startDateTime and update_time<=:endDateTime";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("table", fromTable);
                put("startDateTime", startDateTime);
                put("endDateTime", endDateTime);
            }}, new BeanPropertyRowMapper<>(BollDto.class));
        } catch (Exception e) {
            LOGGER.error("查询BOLL数据失败.", e);
            return null;
        }
    }

    public boolean insetBollBatch(String toTable, List<BollDto> toInsertBoll) {
        try {
            String sql = "INSERT INTO " + toTable +
                    "(market,code,rehab_type,ma20_mid,double_upper,double_lower,one_upper,one_lower,triple_upper,triple_lower,update_time) values(:market,:code,:rehabType,:ma20_mid,:double_upper,:double_lower,:one_upper,:one_lower,:triple_upper,:triple_lower,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(toInsertBoll));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入BOLL数据失败.", e);
            return false;
        }
    }
}
