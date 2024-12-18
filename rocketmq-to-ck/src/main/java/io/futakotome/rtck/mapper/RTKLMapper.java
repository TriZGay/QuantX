package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class RTKLMapper {
    public static final String KL_DAY_TABLE_NAME = "t_kl_day_raw";
    public static final String KL_MIN_5_TABLE_NAME = "t_kl_min_5_raw";
    public static final String KL_WEEK_TABLE_NAME = "t_kl_week_raw";
    public static final String KL_MONTH_TABLE_NAME = "t_kl_month_raw";
    public static final String KL_QUARTER_TABLE_NAME = "t_kl_quarter_raw";
    public static final String KL_YEAR_TABLE_NAME = "t_kl_year_raw";
    public static final String KL_MIN_1_TABLE_NAME = "t_kl_min_1_raw";
    public static final String KL_MIN_3_TABLE_NAME = "t_kl_min_3_raw";
    public static final String KL_MIN_15_TABLE_NAME = "t_kl_min_15_raw";
    public static final String KL_MIN_30_TABLE_NAME = "t_kl_min_30_raw";
    public static final String KL_MIN_60_TABLE_NAME = "t_kl_min_60_raw";

    private static final Logger LOGGER = LoggerFactory.getLogger(RTKLMapper.class);
    private final DataSource dataSource;

    public RTKLMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOne(RTKLDto dto, String tableName) {
        LOGGER.info("K线数据入库:" + dto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into " + tableName +
                            " select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnoverRate,pe,change_rate,update_time,add_time " +
                            " from input('market Int8 ,code String,rehab_type Int8,high_price Float64,open_price Float64,low_price Float64,close_price Float64,last_close_price Float64,volume Int64,turnover Float64,turnoverRate Float64, pe Float64,change_rate Float64 ,update_time DateTime64(3),add_time DateTime64(3)')"
            )) {
                preparedStatement.setInt(1, dto.getMarket());
                preparedStatement.setString(2, dto.getCode());
                preparedStatement.setInt(3, dto.getRehabType());
                preparedStatement.setDouble(4, dto.getHighPrice());
                preparedStatement.setDouble(5, dto.getOpenPrice());
                preparedStatement.setDouble(6, dto.getLowPrice());
                preparedStatement.setDouble(7, dto.getClosePrice());
                preparedStatement.setDouble(8, dto.getLastClosePrice());
                preparedStatement.setLong(9, dto.getVolume());
                preparedStatement.setDouble(10, dto.getTurnover());
                preparedStatement.setDouble(11, dto.getTurnoverRate());
                preparedStatement.setDouble(12, dto.getPe());
                preparedStatement.setDouble(13, dto.getChangeRate() == null ? -1 : dto.getChangeRate());
                preparedStatement.setString(14, dto.getUpdateTime());
                preparedStatement.setString(15, dto.getAddTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error(tableName + ",插入K线数据出现错误", throwables);
            return false;
        }
    }
}
