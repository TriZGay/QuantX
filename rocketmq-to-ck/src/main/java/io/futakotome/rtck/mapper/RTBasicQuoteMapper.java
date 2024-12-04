package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RTBasicQuoteMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTBasicQuoteMapper.class);

    private final DataSource dataSource;

    public RTBasicQuoteMapper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOnePlateBasicQuote(RTBasicQuoteDto basicQuoteDto) {
        LOGGER.info("板块实时报价入库:{}", basicQuoteDto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_plate_basic_quote_raw " +
                            "select market , code   , price_spread , high_price , open_price , low_price , cur_price , last_close_price , volume , turnover , turnover_rate , amplitude , dark_status , sec_status ,update_time" +
                            " from input('market Int8 , code String , price_spread Float64, high_price Float64, open_price Float64, low_price Float64, cur_price Float64, last_close_price Float64, volume Int64 ,turnover Float64 , turnover_rate Float64 ,amplitude Float64 , dark_status UInt8 , sec_status UInt8 ,update_time DateTime64(3)') "
            )) {
                preparedStatement.setInt(1, basicQuoteDto.getMarket());
                preparedStatement.setString(2, basicQuoteDto.getCode());
                preparedStatement.setDouble(3, basicQuoteDto.getPriceSpread());
                preparedStatement.setDouble(4, basicQuoteDto.getHighPrice());
                preparedStatement.setDouble(5, basicQuoteDto.getOpenPrice());
                preparedStatement.setDouble(6, basicQuoteDto.getLowPrice());
                preparedStatement.setDouble(7, basicQuoteDto.getCurPrice());
                preparedStatement.setDouble(8, basicQuoteDto.getLastClosePrice());
                preparedStatement.setLong(9, basicQuoteDto.getVolume());
                preparedStatement.setDouble(10, basicQuoteDto.getTurnover());
                preparedStatement.setDouble(11, basicQuoteDto.getTurnoverRate());
                preparedStatement.setDouble(12, basicQuoteDto.getAmplitude());
                //类型是无符号8位整数,所以库里是255
                preparedStatement.setInt(13, basicQuoteDto.getDarkStatus() == null ? -1 : basicQuoteDto.getDarkStatus());
                preparedStatement.setInt(14, basicQuoteDto.getSecStatus() == null ? -1 : basicQuoteDto.getSecStatus());
                preparedStatement.setObject(15, basicQuoteDto.getUpdateTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("板块实时报价插入失败", throwables);
            return false;
        }
    }

    public boolean insertOneStockBasicQuote(RTBasicQuoteDto basicQuoteDto) {
        LOGGER.info("正股实时报价入库:{}", basicQuoteDto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_stock_basic_quote_raw " +
                            "select market , code   , price_spread , high_price , open_price , low_price , cur_price , last_close_price , volume , turnover , turnover_rate , amplitude , dark_status , sec_status ,update_time" +
                            " from input('market Int8 , code String , price_spread Float64, high_price Float64, open_price Float64, low_price Float64, cur_price Float64, last_close_price Float64, volume Int64 ,turnover Float64 , turnover_rate Float64 ,amplitude Float64 , dark_status UInt8 , sec_status UInt8 ,update_time DateTime64(3)') "
            )) {
                preparedStatement.setInt(1, basicQuoteDto.getMarket());
                preparedStatement.setString(2, basicQuoteDto.getCode());
                preparedStatement.setDouble(3, basicQuoteDto.getPriceSpread());
                preparedStatement.setDouble(4, basicQuoteDto.getHighPrice());
                preparedStatement.setDouble(5, basicQuoteDto.getOpenPrice());
                preparedStatement.setDouble(6, basicQuoteDto.getLowPrice());
                preparedStatement.setDouble(7, basicQuoteDto.getCurPrice());
                preparedStatement.setDouble(8, basicQuoteDto.getLastClosePrice());
                preparedStatement.setLong(9, basicQuoteDto.getVolume());
                preparedStatement.setDouble(10, basicQuoteDto.getTurnover());
                preparedStatement.setDouble(11, basicQuoteDto.getTurnoverRate());
                preparedStatement.setDouble(12, basicQuoteDto.getAmplitude());
                //类型是无符号8位整数,所以库里是255
                preparedStatement.setInt(13, basicQuoteDto.getDarkStatus() == null ? -1 : basicQuoteDto.getDarkStatus());
                preparedStatement.setInt(14, basicQuoteDto.getSecStatus() == null ? -1 : basicQuoteDto.getSecStatus());
                preparedStatement.setObject(15, basicQuoteDto.getUpdateTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("正股实时报价插入失败", throwables);
            return false;
        }
    }

    public boolean insertOneIndexBasicQuote(RTBasicQuoteDto basicQuoteDto) {
        LOGGER.info("指数实时报价入库:{}", basicQuoteDto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_indies_basic_quote_raw " +
                            "select market , code   , price_spread , high_price , open_price , low_price , cur_price , last_close_price , volume , turnover , turnover_rate , amplitude , dark_status , sec_status ,update_time" +
                            " from input('market Int8 , code String , price_spread Float64, high_price Float64, open_price Float64, low_price Float64, cur_price Float64, last_close_price Float64, volume Int64 ,turnover Float64 , turnover_rate Float64 ,amplitude Float64 , dark_status UInt8 , sec_status UInt8 ,update_time DateTime64(3)') ")) {
                preparedStatement.setInt(1, basicQuoteDto.getMarket());
                preparedStatement.setString(2, basicQuoteDto.getCode());
                preparedStatement.setDouble(3, basicQuoteDto.getPriceSpread());
                preparedStatement.setDouble(4, basicQuoteDto.getHighPrice());
                preparedStatement.setDouble(5, basicQuoteDto.getOpenPrice());
                preparedStatement.setDouble(6, basicQuoteDto.getLowPrice());
                preparedStatement.setDouble(7, basicQuoteDto.getCurPrice());
                preparedStatement.setDouble(8, basicQuoteDto.getLastClosePrice());
                preparedStatement.setLong(9, basicQuoteDto.getVolume());
                preparedStatement.setDouble(10, basicQuoteDto.getTurnover());
                preparedStatement.setDouble(11, basicQuoteDto.getTurnoverRate());
                preparedStatement.setDouble(12, basicQuoteDto.getAmplitude());
                //类型是无符号8位整数,所以库里是255
                preparedStatement.setInt(13, basicQuoteDto.getDarkStatus() == null ? -1 : basicQuoteDto.getDarkStatus());
                preparedStatement.setInt(14, basicQuoteDto.getSecStatus() == null ? -1 : basicQuoteDto.getSecStatus());
                preparedStatement.setObject(15, basicQuoteDto.getUpdateTime());
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("指数实时报价插入失败", throwables);
            return false;
        }
    }
}
