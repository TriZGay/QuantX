package io.futakotome.analyze.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
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

    public List<Object> kLineDistinctCodesCommon(String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select distinct (market,code) from " + tableName
            )) {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Object> codes = new ArrayList<>();
                while (resultSet.next()) {
                    codes.add(resultSet.getArray(1).getArray());
                }
                return codes;
            }
        } catch (SQLException throwables) {
            LOGGER.error("查询K线元数据失败", throwables);
            return null;
        }
    }

    public List<String> indiesDistinctCodes() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select distinct code from t_indies_basic_quote_raw")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<String> distinctCodes = new ArrayList<>();
                while (resultSet.next()) {
                    distinctCodes.add(resultSet.getString("code"));
                }
                return distinctCodes;
            }
        } catch (SQLException throwables) {
            LOGGER.error("查询META数据出现错误", throwables);
            return null;
        }
    }
}
