package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.KLineDto;
import io.futakotome.analyze.mapper.dto.KLineRepeatDto;
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
public class KLineMapper {
    public static final String KL_DAY_TABLE_NAME = "t_kl_day_raw";
    public static final String KL_DAY_ARC_TABLE_NAME = "t_kl_day_arc";
    public static final String KL_WEEK_TABLE_NAME = "t_kl_week_raw";
    public static final String KL_WEEK_ARC_TABLE_NAME = "t_kl_week_arc";
    public static final String KL_MONTH_TABLE_NAME = "t_kl_month_raw";
    public static final String KL_MONTH_ARC_TABLE_NAME = "t_kl_month_arc";
    public static final String KL_QUARTER_TABLE_NAME = "t_kl_quarter_raw";
    public static final String KL_QUARTER_ARC_TABLE_NAME = "t_kl_quarter_arc";
    public static final String KL_YEAR_TABLE_NAME = "t_kl_year_raw";
    public static final String KL_YEAR_ARC_TABLE_NAME = "t_kl_year_arc";
    public static final String KL_MIN_1_TABLE_NAME = "t_kl_min_1_raw";
    public static final String KL_MIN_1_ARC_TABLE_NAME = "t_kl_min_1_arc";
    public static final String KL_MIN_3_TABLE_NAME = "t_kl_min_3_raw";
    public static final String KL_MIN_3_ARC_TABLE_NAME = "t_kl_min_3_arc";
    public static final String KL_MIN_5_TABLE_NAME = "t_kl_min_5_raw";
    public static final String KL_MIN_5_ARC_TABLE_NAME = "t_kl_min_5_arc";
    public static final String KL_MIN_15_TABLE_NAME = "t_kl_min_15_raw";
    public static final String KL_MIN_15_ARC_TABLE_NAME = "t_kl_min_15_arc";
    public static final String KL_MIN_30_TABLE_NAME = "t_kl_min_30_raw";
    public static final String KL_MIN_30_ARC_TABLE_NAME = "t_kl_min_30_arc";
    public static final String KL_MIN_60_TABLE_NAME = "t_kl_min_60_raw";
    public static final String KL_MIN_60_ARC_TABLE_NAME = "t_kl_min_60_arc";

    private static final Logger LOGGER = LoggerFactory.getLogger(KLineMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public KLineMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //查询按代码和复权类型的分组数据
    public List<KLineDto> queryKLineGroupByCodeRehabType(String table) {
        try {
            String sql = "select market,code,rehab_type from " + table + " group by (market,code,rehab_type)";
            return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(KLineDto.class));
        } catch (Exception e) {
            LOGGER.error("查询K线按代码和复权类型分组数据错误.", e);
            return null;
        }
    }

    //查询K线归档表里的重复数据
    public List<KLineRepeatDto> queryKLineArchivedRepeated(String start, String end, String tableName) {
        try {
            String sql = "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,repeat" +
                    " from (select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,row_number() OVER (PARTITION BY code, rehab_type, update_time) AS repeat from :tableName prewhere (update_time >= :start) and (update_time <= :end))" +
                    " where repeat > 1";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("tableName", tableName);
                put("start", start);
                put("end", end);
            }}, new BeanPropertyRowMapper<>(KLineRepeatDto.class));
        } catch (Exception e) {
            LOGGER.error("勘误查询K线数据出错.", e);
            return null;
        }
    }

    //查询K线归档数据
    public List<KLineDto> queryKLineArchived(KLineDto kLineDto) {
        try {
            String sql = "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,toString(update_time) as update_time" +
                    " from :tableName" +
                    " prewhere (1=1)";
            if (Objects.nonNull(kLineDto.getCode())) {
                sql += " and (code = :code)";
            }
            if (Objects.nonNull(kLineDto.getRehabType())) {
                sql += " and (rehab_type = :rehabType)";
            }
            if (Objects.nonNull(kLineDto.getStart())) {
                sql += " and (update_time >= :start) ";
            }
            if (Objects.nonNull(kLineDto.getEnd())) {
                sql += " and (update_time <= :end) ";
            }
            sql += "order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(kLineDto),
                    new BeanPropertyRowMapper<>(KLineDto.class));
        } catch (Exception e) {
            LOGGER.error("查询K线数据出错.", e);
            return null;
        }
    }

    public boolean insertBatch(String kTable, List<KLineDto> kLineDtoList) {
        try {
            String sql = "insert into " + kTable
                    + "(market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time)" +
                    " values(:market,:code,:rehabType,:highPrice,:openPrice,:lowPrice,:closePrice,:lastClosePrice,:volume,:turnover,:turnoverRate,:pe,:changeRate,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(kLineDtoList));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入K线数据失败.", e);
            return false;
        }
    }

    public List<KLineDto> prefetchToInsert(String fromTableName, String start, String end) {
        try {
            String sql = " select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,t1.update_time as update_time" +
                    " from :fromTableName as t1 all inner join" +
                    " (select code,rehab_type,update_time,max(add_time) as latest,max(volume) as volume from :fromTableName where (update_time >= :start) and (update_time <= :end) group by code,rehab_type,update_time ) as t2" +
                    " on (t1.code=t2.code) and (t1.rehab_type=t2.rehab_type) and (t1.add_time = t2.latest) and (t1.volume = t2.volume)" +
                    " order by update_time asc";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("fromTableName", fromTableName);
                put("start", start);
                put("end", end);
            }}, new BeanPropertyRowMapper<>(KLineDto.class));
        } catch (Exception e) {
            LOGGER.error("归档K线数据出错.", e);
            return null;
        }
    }

}
