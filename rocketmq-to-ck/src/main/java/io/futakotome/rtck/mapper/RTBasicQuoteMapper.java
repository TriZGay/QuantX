package io.futakotome.rtck.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RTBasicQuoteMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTBasicQuoteMapper.class);

    private final ClickHouseDataSource dataSource;

    public RTBasicQuoteMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOne(RTBasicQuoteDto basicQuoteDto) {
        LOGGER.info("实时报价入库:{}", basicQuoteDto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_indies_basic_quote_raw " +
                            "select market , code , is_suspended , list_time , price_spread , high_price , open_price , low_price , cur_price , last_close_price , volume , turnover , turnover_rate , amplitude , dark_status , sec_status ,update_time" +
                            " from input('market Int8 , code String , is_suspended UInt8 , list_time Date, price_spread Float64, high_price Float64, open_price Float64, low_price Float64, cur_price Float64, last_close_price Float64, volume Int64 ,turnover Float64 , turnover_rate Float64 ,amplitude Float64 , dark_status UInt8 , sec_status UInt8 ,update_time DateTime64(3)') ")) {
                preparedStatement.setInt(1, basicQuoteDto.getMarket());
                preparedStatement.setString(2, basicQuoteDto.getCode());
                preparedStatement.setInt(3, basicQuoteDto.getIsSuspended());
                preparedStatement.setObject(4, basicQuoteDto.getListTime());
                preparedStatement.setDouble(5, basicQuoteDto.getPriceSpread());
                preparedStatement.setDouble(6, basicQuoteDto.getHighPrice());
                preparedStatement.setDouble(7, basicQuoteDto.getOpenPrice());
                preparedStatement.setDouble(8, basicQuoteDto.getLowPrice());
                preparedStatement.setDouble(9, basicQuoteDto.getCurPrice());
                preparedStatement.setDouble(10, basicQuoteDto.getLastClosePrice());
                preparedStatement.setLong(11, basicQuoteDto.getVolume());
                preparedStatement.setDouble(12, basicQuoteDto.getTurnover());
                preparedStatement.setDouble(13, basicQuoteDto.getTurnoverRate());
                preparedStatement.setDouble(14,basicQuoteDto.getAmplitude());
                preparedStatement.setInt(15, basicQuoteDto.getDarkStatus());
                preparedStatement.setInt(16, basicQuoteDto.getSecStatus());
                preparedStatement.setObject(17, basicQuoteDto.getUpdateTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("实时报价插入失败", throwables);
            return false;
        }
    }
}
