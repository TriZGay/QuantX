package io.futakotome.rtck.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.rtck.mapper.dto.CapitalFlowDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CapitalFlowMapper {
    public static final Logger LOGGER = LoggerFactory.getLogger(CapitalFlowMapper.class);
    private final ClickHouseDataSource dataSource;

    public CapitalFlowMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insetOneCapitalFlow(CapitalFlowDto capitalFlowDto) {
        LOGGER.info("资金流向数据入库:{}", capitalFlowDto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_capital_flow " +
                            " select market,code,in_flow,main_in_flow,super_in_flow,big_in_flow,mid_in_flow,sml_in_flow,time,last_valid_time" +
                            " from input('market Int8 , code String ,in_flow Float64,main_in_flow Float64,super_in_flow Float64,big_in_flow Float64,mid_in_flow Float64,sml_in_flow Float64,time DateTime64(3),last_valid_time DateTime64(3)')"
            )) {
                preparedStatement.setInt(1, capitalFlowDto.getMarket());
                preparedStatement.setString(2, capitalFlowDto.getCode());
                preparedStatement.setDouble(3, capitalFlowDto.getInFlow());
                preparedStatement.setDouble(4, capitalFlowDto.getMainInFlow());
                preparedStatement.setDouble(5, capitalFlowDto.getSuperInFlow());
                preparedStatement.setDouble(6, capitalFlowDto.getBigInFlow());
                preparedStatement.setDouble(7, capitalFlowDto.getMidInFlow());
                preparedStatement.setDouble(8, capitalFlowDto.getSmlInFlow());
                preparedStatement.setString(9, capitalFlowDto.getTime());
                preparedStatement.setString(10, capitalFlowDto.getLastValidTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("资金流向数据插入失败", throwables);
            return false;
        }
    }
}
