package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.RTBrokerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RTBrokerMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTBrokerMapper.class);
    private final DataSource dataSource;

    public RTBrokerMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOne(RTBrokerDto dto) {
        LOGGER.info("经纪人队列入库:{}", dto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_brokers_raw" +
                            " select market,code,broker_id,broker_name,broker_pos,ask_or_bid,order_id,volume" +
                            " from input('market Int8,code String,broker_id Int64,broker_name String,broker_pos Int8,ask_or_bid Int8,order_id Int64,volume Int64')"
            )) {
                preparedStatement.setInt(1, dto.getMarket());
                preparedStatement.setString(2, dto.getCode());
                preparedStatement.setLong(3, dto.getBrokerId());
                preparedStatement.setString(4, dto.getBrokerName());
                preparedStatement.setInt(5, dto.getBrokerPos());
                preparedStatement.setInt(6, dto.getAskOrBid());
                preparedStatement.setLong(7, dto.getOrderId() == null ? -1 : dto.getOrderId());
                preparedStatement.setLong(8, dto.getVolume() == null ? -1 : dto.getVolume());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("经纪队列数据入库失败", throwables);
            return false;
        }
    }
}
