package io.futakotome.analyze.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.analyze.mapper.dto.MetaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MetaDataMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaDataMapper.class);
    private final ClickHouseDataSource dataSource;

    public MetaDataMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<MetaDto> kLineDistinctCodesCommon(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select distinct (market,code) from " + tableName
            )) {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<MetaDto> codes = new ArrayList<>();
                while (resultSet.next()) {
                    List<?> tuple = resultSet.getObject(1, List.class);
                    MetaDto metaDto = new MetaDto();
                    metaDto.setMarket(Byte.toUnsignedInt((Byte) tuple.get(0)));
                    metaDto.setCode((String) tuple.get(1));
                    codes.add(metaDto);
                }
                return codes;
            }
        } catch (SQLException throwables) {
            LOGGER.error("查询K线元数据失败", throwables);
            return null;
        }
    }

}
