package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.SnapshotPriceChangeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class StatisticsMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StatisticsMapper(@Qualifier("futuNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<SnapshotPriceChangeDto> queryPriceChangeInPlatesByMarket(Integer market) {
        try {
            String sql = "select market,code,name,update_time,round(((cur_price-last_close_price) /last_close_price)::numeric *100,2)  as price_change from t_snapshot_base where market = :market and type = 7";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("market", market);
            }}, new BeanPropertyRowMapper<>(SnapshotPriceChangeDto.class));
        } catch (Exception e) {
            LOGGER.error("查询板块涨跌幅失败.", e);
            return null;
        }
    }

}
