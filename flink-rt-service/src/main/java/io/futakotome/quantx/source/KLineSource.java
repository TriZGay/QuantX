package io.futakotome.quantx.source;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.quantx.dto.KLineDto;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class KLineSource extends RichSourceFunction<KLineDto> {
    private static final Properties AUTH = new Properties();
    private static ClickHouseDataSource dataSource;

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    static {
        try {
            AUTH.put("user", "default");
            AUTH.put("password", "123456");
            dataSource = new ClickHouseDataSource("jdbc:ch://172.23.160.159:8123/quantx", AUTH);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = dataSource.getConnection();
        preparedStatement = connection.prepareStatement(
                "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time"
                        + " from t_kl_day_raw as t1"
                        + " all inner join"
                        + " (select update_time ,max(add_time) as latest from t_kl_day_raw group by update_time) as t2"
                        + " on (t2.update_time = t1.update_time ) and (t2.latest = t1.add_time)"
                        + " order by t1.update_time"
        );
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (connection != null) connection.close();
        if (preparedStatement != null) preparedStatement.close();
    }

    @Override
    public void run(SourceContext<KLineDto> ctx) throws Exception {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            KLineDto kLineDto = new KLineDto();
            kLineDto.setMarket(resultSet.getInt(1));
            kLineDto.setCode(resultSet.getString(2));
            kLineDto.setRehabType(resultSet.getInt(3));
            kLineDto.setHighPrice(resultSet.getDouble(4));
            kLineDto.setOpenPrice(resultSet.getDouble(5));
            kLineDto.setLowPrice(resultSet.getDouble(6));
            kLineDto.setClosePrice(resultSet.getDouble(7));
            kLineDto.setLastClosePrice(resultSet.getDouble(8));
            kLineDto.setVolume(resultSet.getLong(9));
            kLineDto.setTurnover(resultSet.getDouble(10));
            kLineDto.setTurnoverRate(resultSet.getDouble(11));
            kLineDto.setPe(resultSet.getDouble(12));
            kLineDto.setChangeRate(resultSet.getDouble(13));
            kLineDto.setUpdateTime(resultSet.getString(14));
            kLineDto.setAddTime(resultSet.getString(15));
            ctx.collect(kLineDto);
        }
    }

    @Override
    public void cancel() {

    }
}
