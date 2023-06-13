package io.futakotome.rtck.dao;

import com.clickhouse.jdbc.ClickHouseDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CkQuantXMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CkQuantXMapper.class);
    private final ClickHouseDataSource dataSource;

    public CkQuantXMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement=connection.prepareStatement();
            statement.executeBatch()
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
