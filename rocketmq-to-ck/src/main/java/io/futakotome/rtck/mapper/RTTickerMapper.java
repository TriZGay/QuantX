package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.RTTickerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RTTickerMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTTickerMapper.class);
    private final DataSource dataSource;

    public RTTickerMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOne(RTTickerDto dto) {
        LOGGER.info("逐笔数据入库" + dto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_ticker_raw" +
                            " select market,code,sequence,ticker_direction,price,volume,turnover,ticker_type,type_sign,update_time" +
                            " from input('market Int8,code String,sequence Int64,ticker_direction Int8,price Float64,volume Int64,turnover Float64,ticker_type Int8,type_sign Int8,update_time DateTime64(3)') "
            )) {
                preparedStatement.setInt(1, dto.getMarket());
                preparedStatement.setString(2, dto.getCode());
                preparedStatement.setLong(3, dto.getSequence());
                preparedStatement.setInt(4, dto.getTickerDirection());
                preparedStatement.setDouble(5, dto.getPrice());
                preparedStatement.setLong(6, dto.getVolume());
                preparedStatement.setDouble(7, dto.getTurnover());
                preparedStatement.setInt(8, dto.getTickerType());
                preparedStatement.setInt(9, dto.getTypeSign());
                preparedStatement.setString(10, dto.getUpdateTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("插入逐笔数据失败", throwables);
            return false;
        }
    }
}
