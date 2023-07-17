package io.futakotome.analyze.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.analyze.controller.vo.KLineRequest;
import io.futakotome.analyze.mapper.dto.KLineDto;
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
public class KLineMapper {
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

    private static final Logger LOGGER = LoggerFactory.getLogger(KLineMapper.class);
    private final ClickHouseDataSource dataSource;

    public KLineMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<KLineDto> queryKLineCommon(KLineRequest request, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            List<KLineDto> kLineDtos = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select market,code,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time" +
                            " from " + tableName +
                            " prewhere code = ? and (update_time > ?) and (update_time < ?)"
            )) {
                preparedStatement.setString(1, request.getCode());
                preparedStatement.setString(2, request.getStart());
                preparedStatement.setString(3, request.getEnd());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    KLineDto kLineDto = new KLineDto();
                    kLineDto.setMarket(resultSet.getInt(1));
                    kLineDto.setCode(resultSet.getString(2));
                    kLineDto.setHighPrice(resultSet.getDouble(3));
                    kLineDto.setOpenPrice(resultSet.getDouble(4));
                    kLineDto.setLowPrice(resultSet.getDouble(5));
                    kLineDto.setClosePrice(resultSet.getDouble(6));
                    kLineDto.setLastClosePrice(resultSet.getDouble(7));
                    kLineDto.setVolume(resultSet.getLong(8));
                    kLineDto.setTurnover(resultSet.getDouble(9));
                    kLineDto.setTurnoverRate(resultSet.getDouble(10));
                    kLineDto.setPe(resultSet.getDouble(11));
                    kLineDto.setChangeRate(resultSet.getDouble(12));
                    kLineDto.setUpdateTime(resultSet.getString(13));
                    kLineDtos.add(kLineDto);
                }
            }
            return kLineDtos;
        } catch (SQLException throwables) {
            LOGGER.error("查询K线数据出错", throwables);
            return null;
        }
    }


}
