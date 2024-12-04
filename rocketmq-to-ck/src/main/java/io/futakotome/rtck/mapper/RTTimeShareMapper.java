package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.RTTimeShareDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RTTimeShareMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTTimeShareMapper.class);
    private final DataSource dataSource;

    public RTTimeShareMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOne(RTTimeShareDto dto) {
        LOGGER.info("分时数据入库" + dto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_timeshare_raw" +
                            " select market,code,minute,price,last_close_price,avg_price,volume,turnover,update_time" +
                            " from input('market Int8,code String,minute Int32,price Float64,last_close_price Float64,avg_price Float64,volume Int64,turnover Float64,update_time DateTime64(3) ')"
            )) {
                preparedStatement.setInt(1, dto.getMarket());
                preparedStatement.setString(2, dto.getCode());
                preparedStatement.setInt(3, dto.getMarket());
                preparedStatement.setDouble(4, dto.getPrice());
                preparedStatement.setDouble(5, dto.getLastClosePrice());
                preparedStatement.setDouble(6, dto.getAvgPrice());
                preparedStatement.setLong(7, dto.getVolume());
                preparedStatement.setDouble(8, dto.getTurnover());
                preparedStatement.setString(9, dto.getUpdateTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("分时数据入库失败", throwables);
            return false;
        }
    }
}
