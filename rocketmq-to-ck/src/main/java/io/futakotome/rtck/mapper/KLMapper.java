package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class KLMapper {
    public static final String KL_DAY_TABLE_NAME = "t_kl_day_raw";
    public static final String KL_MIN_5_TABLE_NAME = "t_kl_min_5_raw";
    public static final String KL_WEEK_TABLE_NAME = "t_kl_week_raw";
    public static final String KL_MONTH_TABLE_NAME = "t_kl_month_raw";
    public static final String KL_QUARTER_TABLE_NAME = "t_kl_quarter_raw";
    public static final String KL_YEAR_TABLE_NAME = "t_kl_year_raw";
    public static final String KL_MIN_1_RAW_TABLE_NAME = "t_kl_min_1_raw";
    public static final String KL_MIN_1_ARC_TABLE_NAME = "t_kl_min_1_arc";
    public static final String KL_MIN_3_TABLE_NAME = "t_kl_min_3_raw";
    public static final String KL_MIN_15_TABLE_NAME = "t_kl_min_15_raw";
    public static final String KL_MIN_30_TABLE_NAME = "t_kl_min_30_raw";
    public static final String KL_MIN_60_TABLE_NAME = "t_kl_min_60_raw";

    private static final Logger LOGGER = LoggerFactory.getLogger(KLMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public KLMapper(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean historyInsertBatch(List<RTKLDto> dtos, String tableName) {
        try {
            String sql = "insert into " + tableName
                    + "(market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time)" +
                    " values(:market,:code,:rehabType,:highPrice,:openPrice,:lowPrice,:closePrice,:lastClosePrice,:volume,:turnover,:turnoverRate,:pe,:changeRate,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(dtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入K线数据失败.", e);
            return false;
        }
    }

    public boolean insertBatch(List<RTKLDto> dtos, String tableName) {
        try {
            String sql = "insert into " + tableName
                    + "(market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time)" +
                    " values(:market,:code,:rehabType,:highPrice,:openPrice,:lowPrice,:closePrice,:lastClosePrice,:volume,:turnover,:turnoverRate,:pe,:changeRate,:updateTime,:addTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(dtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入K线数据失败.", e);
            return false;
        }
    }

    @Deprecated
    public boolean insertOne(RTKLDto dto, String tableName) {
        try {
            String sql = "insert into " + tableName + "(market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time)" +
                    " values (:market,:code,:rehabType,:highPrice,:openPrice,:lowPrice,:closePrice,:lastClosePrice,:volume,:turnover,:turnoverRate,:pe,:changeRate,:updateTime,:addTime)";
            int insertRow = namedParameterJdbcTemplate.update(sql, new HashMap<>() {{
                put("market", dto.getMarket());
                put("code", dto.getCode());
                put("rehabType", dto.getRehabType());
                put("highPrice", dto.getHighPrice());
                put("openPrice", dto.getOpenPrice());
                put("lowPrice", dto.getLowPrice());
                put("closePrice", dto.getClosePrice());
                put("lastClosePrice", dto.getLastClosePrice());
                put("volume", dto.getVolume());
                put("turnover", dto.getTurnover());
                put("turnoverRate", dto.getTurnoverRate());
                put("pe", dto.getPe());
                put("changeRate", dto.getChangeRate() == null ? -1 : dto.getChangeRate());
                put("updateTime", dto.getUpdateTime());
                put("addTime", dto.getAddTime());
            }});
            return insertRow > 0;
        } catch (Exception e) {
            LOGGER.error("插入K线数据出现错误:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
