package io.futakotome.analyze.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BaseQuoteMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseQuoteMapper.class);

//    private final DataSource dataSource;
//
//    public BaseQuoteMapper(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public List<BasicQuoteDto> queryListConditional(BaseQuoteRequest baseQuoteRequest) {
//        try (Connection connection = dataSource.getConnection()) {
//            List<BasicQuoteDto> basicQuoteDtoList = new ArrayList<>();
//            try (PreparedStatement preparedStatement = connection.prepareStatement("select market,code,price_spread,high_price,open_price,low_price,cur_price,last_close_price,volume,turnover,turnover_rate,amplitude,dark_status,sec_status,update_time " +
//                    "from t_indies_basic_quote_raw " +
//                    "prewhere code = ? " +
//                    "where (code = ?) and (update_time > ?) and (update_time < ?)")) {
//                preparedStatement.setString(1, baseQuoteRequest.getCode());
//                preparedStatement.setString(2, baseQuoteRequest.getCode());
//                preparedStatement.setString(3, baseQuoteRequest.getStart());
//                preparedStatement.setString(4, baseQuoteRequest.getEnd());
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    BasicQuoteDto basicQuoteDto = new BasicQuoteDto();
//                    basicQuoteDto.setMarket(resultSet.getInt(1));
//                    basicQuoteDto.setCode(resultSet.getString(2));
//                    basicQuoteDto.setPriceSpread(resultSet.getDouble(3));
//                    basicQuoteDto.setHighPrice(resultSet.getDouble(4));
//                    basicQuoteDto.setOpenPrice(resultSet.getDouble(5));
//                    basicQuoteDto.setLowPrice(resultSet.getDouble(6));
//                    basicQuoteDto.setCurPrice(resultSet.getDouble(7));
//                    basicQuoteDto.setLastClosePrice(resultSet.getDouble(8));
//                    basicQuoteDto.setVolume(resultSet.getLong(9));
//                    basicQuoteDto.setTurnover(resultSet.getDouble(10));
//                    basicQuoteDto.setTurnoverRate(resultSet.getDouble(11));
//                    basicQuoteDto.setAmplitude(resultSet.getDouble(12));
//                    basicQuoteDto.setDarkStatus(resultSet.getInt(13));
//                    basicQuoteDto.setSecStatus(resultSet.getInt(14));
//                    basicQuoteDto.setUpdateTime(resultSet.getString(15));
//                    basicQuoteDtoList.add(basicQuoteDto);
//                }
//            }
//            return basicQuoteDtoList;
//        } catch (SQLException throwables) {
//            LOGGER.error("查询基础报价发生错误.", throwables);
//            return null;
//        }
//    }
}
