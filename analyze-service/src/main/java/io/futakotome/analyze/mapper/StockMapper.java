package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.StockDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class StockMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StockMapper(@Qualifier("futuNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public StockDto queryByCode(String code) {
        try {
            String sql = "select name from t_stock where code = :code";
            return namedParameterJdbcTemplate.queryForObject(sql, new HashMap<>() {{
                put("code", code);
            }}, new BeanPropertyRowMapper<>(StockDto.class));
        } catch (Exception e) {
            LOGGER.error("查询标的失败.", e);
            return null;
        }
    }
}
