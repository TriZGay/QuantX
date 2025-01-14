package io.futakotome.analyze.mapper;

import io.futakotome.analyze.controller.vo.KLineRequest;
import io.futakotome.analyze.mapper.dto.KLineDto;
import io.futakotome.analyze.mapper.dto.KLineRepeatDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
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

    private Integer fillStep(String archedTableName) {
        switch (archedTableName) {
            case KL_MIN_1_ARC_TABLE_NAME:
                return 60;
            case KL_MIN_3_ARC_TABLE_NAME:
                return 60 * 3;
            case KL_MIN_5_ARC_TABLE_NAME:
                return 60 * 5;
            case KL_MIN_15_ARC_TABLE_NAME:
                return 60 * 15;
            case KL_MIN_30_ARC_TABLE_NAME:
                return 60 * 30;
            case KL_MIN_60_ARC_TABLE_NAME:
                return 60 * 60;
            case KL_DAY_ARC_TABLE_NAME:
                return 60 * 60 * 24;
            case KL_MONTH_ARC_TABLE_NAME:
                return 60 * 60 * 24 * 30;
            case KL_WEEK_ARC_TABLE_NAME:
                return 60 * 60 * 24 * 7;
            case KL_QUARTER_ARC_TABLE_NAME:
                return 60 * 60 * 24 * 120;
            case KL_YEAR_ARC_TABLE_NAME:
                return 60 * 60 * 24 * 365;
        }
        return 0;
    }

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

    public List<KLineDto> queryKLineArchived(String start, String end, String tableName) {
        try {
            String sql = "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time" +
                    " from :tableName" +
                    " prewhere (update_time >= :start) and (update_time <= :end) order by update_time asc";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("tableName", tableName);
                put("start", start);
                put("end", end);
            }}, new BeanPropertyRowMapper<>(KLineDto.class));
        } catch (Exception e) {
            LOGGER.error("查询K线数据出错.", e);
            return null;
        }
    }

    public List<KLineDto> queryKLineArchived(KLineDto kLineDto) {
        try {
            String sql = "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time" +
                    " from :tableName" +
                    " prewhere (code = :code) and (rehab_type = :rehabType) and (update_time >= :start) and (update_time <= :end) order by update_time asc";
            return namedParameterJdbcTemplate.query(sql,
                    new BeanPropertySqlParameterSource(kLineDto),
                    new BeanPropertyRowMapper<>(KLineDto.class));
        } catch (Exception e) {
            LOGGER.error("查询K线数据出错.", e);
            return null;
        }
    }

    public Integer kLinesRawTransToArc(String fromTableName, String toTableName, String start, String end) {
        try {
            String sql = "insert into " + toTableName +
                    " select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,t1.update_time as update_time" +
                    " from :fromTableName as t1 all inner join" +
                    " (select code,rehab_type,update_time,max(add_time) as latest,max(volume) as volume from :fromTableName where (update_time >= :start) and (update_time <= :end) group by code,rehab_type,update_time ) as t2" +
                    " on (t1.code=t2.code) and (t1.rehab_type=t2.rehab_type) and (t1.add_time = t2.latest) and (t1.volume = t2.volume)" +
                    " order by update_time asc";
            return namedParameterJdbcTemplate.update(sql, new HashMap<>() {{
                put("fromTableName", fromTableName);
                put("toTableName", toTableName);
                put("start", start);
                put("end", end);
            }});
        } catch (Exception e) {
            LOGGER.error("归档K线数据出错.", e);
            return null;
        }
    }

}
