package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.AnaDatabaseInfoDto;
import io.futakotome.analyze.mapper.dto.AnaTableInfoDto;
import io.futakotome.analyze.mapper.dto.MetaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class MetaDataMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaDataMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MetaDataMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<AnaTableInfoDto> tableInfo(String tableName) {
        try {
            String sql = "select max (update_time) as max_time, min (update_time) as min_time ,code,rehab_type " +
                    " from :table group by code,rehab_type order by code";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("table", tableName);
            }}, new BeanPropertyRowMapper<>(AnaTableInfoDto.class));
        } catch (Exception e) {
            LOGGER.error("查询表信息失败.", e);
            return null;
        }
    }

    public List<AnaDatabaseInfoDto> dbInfo() {
        try {
            String sql = "select database,table,formatReadableSize(size) AS size,formatReadableSize(bytes_on_disk) AS bytes_on_disk,formatReadableSize(data_uncompressed_bytes) AS data_uncompressed_bytes,formatReadableSize(data_compressed_bytes) AS data_compressed_bytes,round(compress_rate,2) AS compressed_rate,rows" +
                    " from (" +
                    "select database,table,sum(bytes) AS size,sum(rows) AS rows,sum(bytes_on_disk) AS bytes_on_disk,sum(data_uncompressed_bytes) AS data_uncompressed_bytes,sum(data_compressed_bytes) AS data_compressed_bytes,(data_compressed_bytes / data_uncompressed_bytes) * 100 AS compress_rate" +
                    " from system.parts" +
                    " where database='quantx'" +
                    " group by database,table" +
                    ")";
            return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AnaDatabaseInfoDto.class));
        } catch (Exception e) {
            LOGGER.error("查询数据库信息失败.", e);
            return null;
        }
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
