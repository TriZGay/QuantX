package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.EmaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EmaMapper {
    public static final String EMA_MIN_1_TABLE = "t_ema_min_1_arc";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmaMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EmaMapper(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insertBatch(List<EmaDto> toAddEmas, String table) {
        try {
            String sql = "insert into " + table
                    + "(market,code,rehab_type,ema_5,ema_10,ema_12,ema_20,ema_26,ema_60,ema_120,update_time,add_time)" +
                    " values (:market,:code,:rehabType,:ema_5,:ema_10,:ema_12,:ema_20,:ema_26,:ema_60,:ema_120,:updateTime,:addTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(toAddEmas));
            LOGGER.info("插入EMA数据成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入EMA数据失败.", e);
            return false;
        }
    }

    public List<EmaDto> queryLatest(String table) {
        try {
            String sql = "select market,code,rehab_type,ema_5, ema_10,ema_12,ema_20, ema_26, ema_60, ema_120,toString(update_time) as update_time\n" +
                    "from t_ema_min_1_arc e inner join (select code, rehab_type, max(update_time) as update_time" +
                    "      from " + table +
                    "      group by (code, rehab_type)) e2" +
                    "     on e.code = e2.code and e.rehab_type = e2.rehab_type and e.update_time = e2.update_time";
            return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EmaDto.class));
        } catch (Exception e) {
            LOGGER.error("查询ema最新数据失败", e);
            return null;
        }
    }

}
