package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.MetaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class MetaDataMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaDataMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MetaDataMapper(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<String> tables() {
        try {
            String sql = "show tables";
            return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> rs.getString(1));
        } catch (Exception e) {
            LOGGER.error("show tables失败.", e);
            return null;
        }
    }

    public List<MetaDto> kLineDistinctCodesCommon(String tableName) {
        try {
            String sql = "select distinct (market,code) from :tableName";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("tableName", tableName);
            }}, (rs, rowNum) -> {
                List<?> tuple = rs.getObject(1, List.class);
                MetaDto metaDto = new MetaDto();
                metaDto.setMarket(Byte.toUnsignedInt((Byte) tuple.get(0)));
                metaDto.setCode((String) tuple.get(1));
                return metaDto;
            });
        } catch (Exception e) {
            LOGGER.error("查询K线元数据失败.", e);
            return null;
        }
    }

}
