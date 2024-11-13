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
    public static final String KL_WEEK_TABLE_NAME = "t_kl_week_raw";
    public static final String KL_MONTH_TABLE_NAME = "t_kl_month_raw";
    public static final String KL_QUARTER_TABLE_NAME = "t_kl_quarter_raw";
    public static final String KL_YEAR_TABLE_NAME = "t_kl_year_raw";
    public static final String KL_MIN_1_TABLE_NAME = "t_kl_min_1_raw";
    public static final String KL_MIN_1_ARC_TABLE_NAME = "t_kl_min_1_arc";
    public static final String KL_MIN_3_TABLE_NAME = "t_kl_min_3_raw";
    public static final String KL_MIN_5_TABLE_NAME = "t_kl_min_5_raw";
    public static final String KL_MIN_15_TABLE_NAME = "t_kl_min_15_raw";
    public static final String KL_MIN_30_TABLE_NAME = "t_kl_min_30_raw";
    public static final String KL_MIN_60_TABLE_NAME = "t_kl_min_60_raw";

    private static final Logger LOGGER = LoggerFactory.getLogger(KLineMapper.class);
    private final ClickHouseDataSource dataSource;

    public KLineMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Integer fillStep(String archedTableName) {
        if (archedTableName.equals(KL_MIN_1_ARC_TABLE_NAME)) {
            return 60;
        }
        return 0;
    }

    public List<KLineDto> queryKLineArchived(KLineRequest request, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            List<KLineDto> result = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time" +
                            " from " + tableName +
                            " prewhere (code = ?) and (rehab_type = ?) and (update_time >= ?) and (update_time <= ?) order by update_time asc" +
                            " with fill from toDateTime64(?,3) to toDateTime64(?,3) step ?;"
            )) {
                preparedStatement.setString(1, request.getCode());
                preparedStatement.setInt(2, request.getRehabType());
                preparedStatement.setString(3, request.getStart());
                preparedStatement.setString(4, request.getEnd());
                preparedStatement.setString(5, request.getStart());
                preparedStatement.setString(6, request.getEnd());
                preparedStatement.setInt(7, fillStep(tableName));
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    KLineDto kLineDto = new KLineDto();
                    kLineDto.setMarket(resultSet.getInt(1));
                    kLineDto.setCode(resultSet.getString(2));
                    kLineDto.setRehabType(resultSet.getInt(3));
                    kLineDto.setHighPrice(resultSet.getDouble(4) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(4)));
                    kLineDto.setOpenPrice(resultSet.getDouble(5) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(5)));
                    kLineDto.setLowPrice(resultSet.getDouble(6) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(6)));
                    kLineDto.setClosePrice(resultSet.getDouble(7) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(7)));
                    kLineDto.setLastClosePrice(resultSet.getDouble(8));
                    kLineDto.setVolume(resultSet.getLong(9));
                    kLineDto.setTurnover(resultSet.getDouble(10));
                    kLineDto.setTurnoverRate(resultSet.getDouble(11));
                    kLineDto.setPe(resultSet.getDouble(12));
                    kLineDto.setChangeRate(resultSet.getDouble(13));
                    kLineDto.setUpdateTime(resultSet.getString(14));
                    result.add(kLineDto);
                }
            }
            return result;
        } catch (SQLException throwables) {
            LOGGER.error("查询K线数据出错", throwables);
            return null;
        }
    }

    public List<KLineDto> queryKLineCommon(KLineRequest request, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            List<KLineDto> kLineDtos = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time" +
                            " from " + tableName + " as t1" +
                            " all inner join" +
                            " (select update_time ,max(add_time) as latest from " + tableName + " prewhere code = ? group by update_time) as t2 " +
                            " on (t2.update_time = t1.update_time ) and (t2.latest = t1.add_time) and (code = ? ) and (rehab_type = ?) and (t1.update_time >= ?) and (t1.update_time <= ?)" +
                            " order by t1.update_time"
            )) {
                preparedStatement.setString(1, request.getCode());
                preparedStatement.setString(2, request.getCode());
                preparedStatement.setInt(3, request.getRehabType());
                preparedStatement.setString(4, request.getStart());
                preparedStatement.setString(5, request.getEnd());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    KLineDto kLineDto = new KLineDto();
                    kLineDto.setMarket(resultSet.getInt(1));
                    kLineDto.setCode(resultSet.getString(2));
                    kLineDto.setRehabType(resultSet.getInt(3));
                    kLineDto.setHighPrice(resultSet.getDouble(4) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(4)));
                    kLineDto.setOpenPrice(resultSet.getDouble(5) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(5)));
                    kLineDto.setLowPrice(resultSet.getDouble(6) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(6)));
                    kLineDto.setClosePrice(resultSet.getDouble(7) == 0D ? "-"
                            : String.valueOf(resultSet.getDouble(7)));
                    kLineDto.setLastClosePrice(resultSet.getDouble(8));
                    kLineDto.setVolume(resultSet.getLong(9));
                    kLineDto.setTurnover(resultSet.getDouble(10));
                    kLineDto.setTurnoverRate(resultSet.getDouble(11));
                    kLineDto.setPe(resultSet.getDouble(12));
                    kLineDto.setChangeRate(resultSet.getDouble(13));
                    kLineDto.setUpdateTime(resultSet.getString(14));
                    kLineDto.setAddTime(resultSet.getString(15));
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
