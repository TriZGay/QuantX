package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.MaNDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class MaNMapper {
    public static final String MA_MIN_1_TABLE_NAME = "t_ma_min_1_arc";

    private static final Logger LOGGER = LoggerFactory.getLogger(MaNMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MaNMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insetMaBatch(String toTableName, List<MaNDto> maNDtos) {
        try {
            String sql = "insert into " + toTableName
                    + "(market,code,rehab_type,ma_5,ma_10,ma_20,ma_30,ma_60,ma_120,update_time) values(:market,:code,:rehabType,:ma_5,:ma_10,:ma_20,:ma_30,:ma_60,:ma_120,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(maNDtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入MA数据失败.", e);
            return false;
        }
    }

    public List<MaNDto> queryMa12AndMa26UseKArc(String kTableName, String code, Integer rehabType, String startDateTime, String endDateTime) {
        try {
            String sql = "select market,code,rehab_type," +
                    "avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 11 following) as ma_12," +
                    "avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 25 following) as ma_26," +
                    "update_time" +
                    " from :tableName" +
                    " prewhere (code=:code) and (rehab_type=:rehabType) and (update_time >= :start) and (update_time <= :end) order by update_time asc ";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("tableName", kTableName);
                put("code", code);
                put("rehabType", rehabType);
                put("start", startDateTime);
                put("end", endDateTime);
            }}, new BeanPropertyRowMapper<>(MaNDto.class));
        } catch (Exception e) {
            LOGGER.error("查询MA数据失败.", e);
            return null;
        }
    }

    public List<MaNDto> queryMaUseKArc(String fromTableName, String startDateTime, String endDateTime) {
        try {
            String sql = " select market,code,rehab_type," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 4 following),4) as ma_5," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 9 following),4) as ma_10," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as ma_20," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 29 following),4) as ma_30," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 59 following),4) as ma_60," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 119 following),4) as ma_120," +
                    "update_time" +
                    " from :tableName" +
                    " prewhere  (update_time >= :start) and (update_time <= :end) order by update_time asc ";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("tableName", fromTableName);
                put("start", startDateTime);
                put("end", endDateTime);
            }}, new BeanPropertyRowMapper<>(MaNDto.class));
        } catch (Exception e) {
            LOGGER.error("查询MA数据失败.", e);
            return null;
        }
    }

    public List<MaNDto> queryMaN(MaNDto maNDto) {
        try {
            String sql = "select market,code,rehab_type,ma_5,ma_10,ma_20,ma_30,ma_60,ma_120,update_time" +
                    " from :tableName" +
                    " prewhere (1=1) ";
            if (Objects.nonNull(maNDto.getRehabType())) {
                sql += " and (rehab_type = :rehabType) ";
            }
            if (Objects.nonNull(maNDto.getCode())) {
                sql += " and (code = :code) ";
            }
            if (Objects.nonNull(maNDto.getStart())) {
                sql += " and (update_time >= :start) ";
            }
            if (Objects.nonNull(maNDto.getEnd())) {
                sql += " and (update_time <= :end) ";
            }
            sql += " order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(maNDto),
                    new BeanPropertyRowMapper<>(MaNDto.class));
        } catch (Exception e) {
            LOGGER.error("查询MA数据失败.", e);
            return null;
        }
    }
}
