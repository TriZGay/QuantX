package io.futakotome.analyze.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.mapper.dto.MaDto;
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
public class MaNMapper {
    public static final String DAY_K_MA5_TABLE_NAME = "t_ma5_day";
    public static final String DAY_K_MA10_TABLE_NAME = "t_ma10_day";
    public static final String DAY_K_MA20_TABLE_NAME = "t_ma20_day";
    public static final String DAY_K_MA30_TABLE_NAME = "t_ma30_day";

    private static final Logger LOGGER = LoggerFactory.getLogger(MaNMapper.class);
    private final ClickHouseDataSource dataSource;

    public MaNMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<MaDto> queryMa5Common(MaRequest maRequest, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select market,code ,rehab_type,update_time,round(avg (close_price) over (partition by (code,rehab_type) order by update_time desc rows BETWEEN 0 preceding and 4 following ),4) as ma5 " +
                            " from " + tableName +
                            " prewhere (rehab_type = ?)and ( code = ? )AND (update_time >= ?) AND (update_time <= ?) "
            )) {
                preparedStatement.setInt(1, maRequest.getRehabType());
                preparedStatement.setString(2, maRequest.getCode());
                preparedStatement.setString(3, maRequest.getStart());
                preparedStatement.setString(4, maRequest.getEnd());
                ResultSet resultSet = preparedStatement.executeQuery();
                List<MaDto> maDtos = new ArrayList<>();
                while (resultSet.next()) {
                    MaDto maDto = new MaDto();
                    maDto.setMarket(resultSet.getInt(1));
                    maDto.setCode(resultSet.getString(2));
                    maDto.setRehabType(resultSet.getInt(3));
                    maDto.setUpdateTime(resultSet.getString(4));
                    maDto.setMaValue(resultSet.getDouble(5));
                    maDtos.add(maDto);
                }
                return maDtos;
            }
        } catch (SQLException throwables) {
            LOGGER.error("查询MA5数据出错", throwables);
            return null;
        }
    }

    public List<MaDto> queryDayMaNCommon(MaRequest maRequest, String tableName) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select market,code,rehab_type,ma_value,update_time,add_time" +
                            " from " + tableName +
                            " prewhere code = ?" +
                            " where (code = ?) and ((rehab_type = ?) and (update_time >= ?) and (update_time <= ?))"
            )) {
                List<MaDto> dayKMaDtos = new ArrayList<>();
                preparedStatement.setString(1, maRequest.getCode());
                preparedStatement.setString(2, maRequest.getCode());
                preparedStatement.setInt(3, maRequest.getRehabType());
                preparedStatement.setString(4, maRequest.getStart());
                preparedStatement.setString(5, maRequest.getEnd());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    MaDto maDto = new MaDto();
                    maDto.setMarket(resultSet.getInt(1));
                    maDto.setCode(resultSet.getString(2));
                    maDto.setRehabType(resultSet.getInt(3));
                    maDto.setMaValue(resultSet.getDouble(4));
                    maDto.setUpdateTime(resultSet.getString(5));
//                    maDto.setAddTime(resultSet.getString(6));
                    dayKMaDtos.add(maDto);
                }
                return dayKMaDtos;
            }
        } catch (SQLException throwables) {
            LOGGER.error("查询日K均线数据出错", throwables);
            return null;
        }
    }
}
