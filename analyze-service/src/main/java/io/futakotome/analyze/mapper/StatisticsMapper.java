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

    public List<SnapshotPriceChangeDto> queryRiseTop10InPlatesByMarket(Integer market) {
        try {
            String sql = "select market,code,name,update_time,coalesce(round(((cur_price-last_close_price) /nullif(last_close_price,0))::numeric *100,2),0)  as price_change from t_snapshot_base where market = :market and type = 7  order by price_change desc limit 10";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("market", market);
            }}, new BeanPropertyRowMapper<>(SnapshotPriceChangeDto.class));
        } catch (Exception e) {
            LOGGER.error("查询板块涨幅失败.", e);
            return null;
        }
    }


    public List<SnapshotPriceChangeDto> queryReduceTop10InPlatesByMarket(Integer market) {
        try {
            String sql = "select market,code,name,update_time,coalesce(round(((cur_price-last_close_price) /nullif(last_close_price,0))::numeric *100,2),0)  as price_change from t_snapshot_base where market = :market and type = 7  order by price_change asc limit 10";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("market", market);
            }}, new BeanPropertyRowMapper<>(SnapshotPriceChangeDto.class));
        } catch (Exception e) {
            LOGGER.error("查询板块跌幅失败.", e);
            return null;
        }
    }

}
