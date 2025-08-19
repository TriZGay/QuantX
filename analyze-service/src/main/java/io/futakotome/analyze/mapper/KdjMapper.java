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

    /**
     * 递归cte计算kdj
     * 三个时间的关系[....startDateTime...toAddDateTime...endDateTime...]
     *
     * @param kdjTable      kdj表
     * @param kTable        k线表
     * @param startDateTime 开始时间,因为需要计算9天的滑动窗口,一般建议开始时间取需要计算的那天的前一天交易日
     * @param endDateTime   结束时间
     * @param toAddDateTime 需要入库的时间段截取,
     * @return
     */
    public List<KdjDto> computeKdj(String kdjTable, String kTable, String code, Integer rehabType, String startDateTime, String endDateTime, String toAddDateTime) {
        try {
            String sql = "with recursive rsv_pre as (" +
                    "    select market, code, rehab_type, high_price, low_price, close_price, update_time, max (high_price) over (order by update_time rows between 8 preceding and current row ) as highest_9, min (low_price) over (order by update_time rows between 8 preceding and current row) as lowest_9" +
                    "    from " + kTable +
                    "    where update_time >= :startDateTime and update_time <= :endDateTime and code = :code and rehab_type = :rehabType" +
                    "    ), rsv_data as (" +
                    "    select *, case" +
                    "    when highest_9 = lowest_9 then 50" +
                    "    else (close_price - lowest_9) / (highest_9 - lowest_9) * 100" +
                    "    end as rsv, row_number() over (order by update_time ) as rn" +
                    "    from rsv_pre" +
                    "    ), connect_data as (" +
                    "    select r.*, kdj.k, kdj.d, kdj.j" +
                    "    from rsv_data r left join " + kdjTable + " kdj on r.code=kdj.code and r.rehab_type=kdj.rehab_type and r.update_time=kdj.update_time " +
                    "    ), kdj_cte as (" +
                    "    select market, code, rehab_type, k, d, j, rsv, update_time, rn" +
                    "    from connect_data" +
                    "    where rn = 1" +
                    "    union all" +
                    "    select market, code, rehab_type, ((2/3)*e.k)+((1/3)*p.rsv) as k, ((2/3)*e.d+(1/3)*k) as d, ((3*k)-(2*d)) as j, rsv, update_time, p.rn" +
                    "    from connect_data p, kdj_cte e" +
                    "    where p.rn - 1 = e.rn" +
                    "    )" +
                    "select market, code, rehab_type, k, d, j, toString(update_time) as update_time " +
                    "from kdj_cte " +
                    "where update_time >= :toAddDateTime";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("code", code);
                put("rehabType", rehabType);
                put("startDateTime", startDateTime);
                put("endDateTime", endDateTime);
                put("toAddDateTime", toAddDateTime);
            }}, new BeanPropertyRowMapper<>(KdjDto.class));
        } catch (Exception e) {
            LOGGER.info("计算KDJ失败.", e);
            return null;
        }
    }

    public Integer insertInitKdjValues(String kdjTable, String kTable) {
        try {
            String sql = "insert into " + kdjTable +
                    " select market, code, rehab_type, 50, 50, 50, update_time" +
                    " from " + kTable +
                    " where update_time =:date";
            return namedParameterJdbcTemplate.update(sql, new HashMap<>() {{
                put("date", "2025-01-02 09:30:00");
            }});
        } catch (Exception e) {
            LOGGER.error("插入KDJ初始值失败.", e);
            return null;
        }
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

    @Deprecated
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
