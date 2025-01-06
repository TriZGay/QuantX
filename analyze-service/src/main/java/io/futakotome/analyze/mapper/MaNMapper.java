package io.futakotome.analyze.mapper;

import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.mapper.dto.MaDto;
import io.futakotome.analyze.mapper.dto.MaNDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class MaNMapper {
    public static final String DAY_K_MA5_TABLE_NAME = "t_ma5_day";
    public static final String DAY_K_MA10_TABLE_NAME = "t_ma10_day";
    public static final String DAY_K_MA20_TABLE_NAME = "t_ma20_day";
    public static final String DAY_K_MA30_TABLE_NAME = "t_ma30_day";

    private static final Logger LOGGER = LoggerFactory.getLogger(MaNMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MaNMapper(@Qualifier("analyzeNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insetMaBatch(String toTableName, List<MaNDto> maNDtos) {
        try {
            String sql = "insert into " + toTableName
                    + "(market,code,rehab_type,ma_5,ma_10,ma_20,ma_30,ma_60,ma_120,update_time) values(:market,:code,:rehabType,:ma_5,:ma_10,:ma_20,:ma_30,:ma_60,:ma_120,:updateTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(maNDtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入MA数据失败.", e);
            return false;
        }
    }

    public List<MaNDto> queryMaUserKArc(String fromTableName, String startDateTime, String endDateTime) {
        try {
            String sql = " select market,code,rehab_type," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 4 following),4) as ma_5," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 9 following),4) as ma_10," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 19 following),4) as ma_20," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 29 following),4) as ma_30," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 59 following),4) as ma_60," +
                    "round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and 119 following),4) as ma_120," +
                    "update_time" +
                    " from :tableName" +
                    " prewhere  (update_time >= :start) and (update_time <= :end) order by update_time asc ";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("tableName", fromTableName);
                put("start", startDateTime);
                put("end", endDateTime);
            }}, new BeanPropertyRowMapper<>(MaNDto.class));
        } catch (Exception e) {
            LOGGER.error("查询MA数据失败.", e);
            return null;
        }
    }

    public List<MaDto> queryMaUserKArc(MaRequest maRequest, String tableName, Integer following) {
        try {
            String sql = "select market,code ,rehab_type,update_time,round(avg(close_price) over (partition by (code,rehab_type) order by update_time desc rows between 0 preceding and :following following),4) as ma_value " +
                    " from :tableName" +
                    " prewhere (rehab_type = :rehabType) and (code = :code) and (update_time >= :start) and (update_time <= :end) order by update_time asc ";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("tableName", tableName);
                put("rehabType", maRequest.getRehabType());
                put("code", maRequest.getCode());
                put("start", maRequest.getStart());
                put("end", maRequest.getEnd());
                put("following", following);
            }}, new BeanPropertyRowMapper<>(MaDto.class));
        } catch (Exception e) {
            LOGGER.error("查询MA数据失败.", e);
            return null;
        }
    }

    public List<MaDto> queryDayMaNCommon(MaRequest maRequest, String tableName) {
        return null;
//        try (Connection connection = dataSource.getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(
//                    "select market,code,rehab_type,ma_value,update_time,add_time" +
//                            " from " + tableName +
//                            " prewhere code = ?" +
//                            " where (code = ?) and ((rehab_type = ?) and (update_time >= ?) and (update_time <= ?))"
//            )) {
//                List<MaDto> dayKMaDtos = new ArrayList<>();
//                preparedStatement.setString(1, maRequest.getCode());
//                preparedStatement.setString(2, maRequest.getCode());
//                preparedStatement.setInt(3, maRequest.getRehabType());
//                preparedStatement.setString(4, maRequest.getStart());
//                preparedStatement.setString(5, maRequest.getEnd());
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    MaDto maDto = new MaDto();
//                    maDto.setMarket(resultSet.getInt(1));
//                    maDto.setCode(resultSet.getString(2));
//                    maDto.setRehabType(resultSet.getInt(3));
//                    maDto.setMaValue(resultSet.getDouble(4));
//                    maDto.setUpdateTime(resultSet.getString(5));
////                    maDto.setAddTime(resultSet.getString(6));
//                    dayKMaDtos.add(maDto);
//                }
//                return dayKMaDtos;
//            }
//        } catch (SQLException throwables) {
//            LOGGER.error("查询日K均线数据出错", throwables);
//            return null;
//        }
    }
}
