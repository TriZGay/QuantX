package io.futakotome.rtck.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RTKLMapper {
    public static final String KL_DAY_TABLE_NAME = "t_kl_day_raw";
    public static final String KL_MIN_5_TABLE_NAME = "t_kl_min_5_raw";

    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLMapper.class);
    private final ClickHouseDataSource dataSource;

    public RTKLMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOne(RTKLDto dto, String tableName) {
        LOGGER.info("K线数据入库:" + dto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into " + tableName +
                            " select market,code,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnoverRate,pe,change_rate,update_time " +
                            " from input('market Int8 ,code String,high_price Float64,open_price Float64,low_price Float64,close_price Float64,last_close_price Float64,volume Int64,turnover Float64,turnoverRate Float64, pe Float64,change_rate Float64 ,update_time DateTime64(3)')"
            )) {
                preparedStatement.setInt(1, dto.getMarket());
                preparedStatement.setString(2, dto.getCode());
                preparedStatement.setDouble(3, dto.getHighPrice());
                preparedStatement.setDouble(4, dto.getOpenPrice());
                preparedStatement.setDouble(5, dto.getLowPrice());
                preparedStatement.setDouble(6, dto.getClosePrice());
                preparedStatement.setDouble(7, dto.getLastClosePrice());
                preparedStatement.setLong(8, dto.getVolume());
                preparedStatement.setDouble(9, dto.getTurnover());
                preparedStatement.setDouble(10, dto.getTurnoverRate());
                preparedStatement.setDouble(11, dto.getPe());
                preparedStatement.setDouble(12, dto.getChangeRate() == null ? -1 : dto.getChangeRate());
                preparedStatement.setString(13, dto.getUpdateTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error(tableName + ",插入K线数据出现错误", throwables);
            return false;
        }
    }
}
