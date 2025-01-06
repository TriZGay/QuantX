package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.TradeDateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
public class TradeDateMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeDateMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TradeDateMapper(@Qualifier("offlineNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TradeDateDto> queryTradeDateByMarketPreceding(Integer preceding, LocalDate aDate, String market) {
        try {
            String sql = "select id,market_or_security,time,trade_date_type from t_trade_date where market_or_security = :market and time < :aDate order by time desc limit :preceding";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("market", market);
                put("aDate", aDate);
                put("preceding", preceding);
            }}, new BeanPropertyRowMapper<>(TradeDateDto.class));
        } catch (Exception e) {
            LOGGER.error("查询交易日期失败.{}", e.getMessage());
            return null;
        }
    }
}
