package io.futakotome.quantx;

import io.futakotome.quantx.dto.TradeDateDto;
import io.futakotome.quantx.fomatters.JdbcFormatter;
import io.futakotome.quantx.operators.KLineOperators;
import io.futakotome.quantx.operators.Ma5Operators;
import io.futakotome.quantx.operators.TradeDateOperators;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple15;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.types.Row;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class DataStreamJob {
    private static final String QUERY_CN_TRADE_DATE = "";
    private static final String QUERY_CN_DAY_K = "";
    private static final String INSERT_CN_MA5 = "insert into t_ma5 values (?,?,?,?,?,?)";

    public static void main(String[] args) throws Exception {
        ParameterTool parameter = ParameterTool.fromPropertiesFile(DataStreamJob.class.getResourceAsStream("/base_config.properties"));
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String firstOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).toString();
        String endOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).toString();
        System.out.println("=====大A市场MA数据开始统计=====");
        System.out.println("[" + firstOfYear + "~" + endOfYear + "]");

        List<TradeDateDto> cnMarketTradeDate = env.createInput(
                JdbcFormatter.inputFromPg(parameter,
                        "select id,market_or_security,time,trade_date_type from t_trade_date where market_or_security = '21,22' and time >= '" + firstOfYear + "'and time <= '" + endOfYear + "' order by time", new RowTypeInfo(
                                BasicTypeInfo.LONG_TYPE_INFO,
                                BasicTypeInfo.STRING_TYPE_INFO,
                                BasicTypeInfo.STRING_TYPE_INFO,
                                BasicTypeInfo.INT_TYPE_INFO
                        )))
                .map(new TradeDateOperators.ToTradeDatePojo())
                .filter(new TradeDateOperators.Between("2023-07-18", "2023-07-31"))
                .first(5).collect();
        if (cnMarketTradeDate.size() < 5) {
            System.err.println("交易日期至少要5天!");
            return;
        }
        TradeDateDto firstTradeDate = cnMarketTradeDate.get(0);
        TradeDateDto endTradeDate = cnMarketTradeDate.get(4);
        MapOperator<Row, Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
                Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> dayK = env.createInput(JdbcFormatter.inputFromClickhouse(
                parameter,
                "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time" +
                        " from t_kl_day_raw as t1" +
                        " all inner join" +
                        " (select update_time ,max(add_time) as latest from t_kl_day_raw group by update_time) as t2 " +
                        " on (t2.update_time = t1.update_time ) and (t2.latest = t1.add_time) and (t1.update_time >= '" + firstTradeDate.getTime() + "') and (t1.update_time <= '" + endTradeDate.getTime() + "')" +
                        " order by t1.update_time",
                new RowTypeInfo(
                        BasicTypeInfo.BYTE_TYPE_INFO,  //market f0
                        BasicTypeInfo.STRING_TYPE_INFO, //code f1
                        BasicTypeInfo.BYTE_TYPE_INFO, //rehabType f2
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //highPrice f3
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //openPrice f4
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //lowPrice f5
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //closePrice f6
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //lastClosePrice f7
                        BasicTypeInfo.LONG_TYPE_INFO, //volume f8
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //turnover f9
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //turnoverRate f10
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //pe f11
                        BasicTypeInfo.DOUBLE_TYPE_INFO, //changeRate f12
                        BasicTypeInfo.of(LocalDateTime.class), //updateTime f13
                        BasicTypeInfo.of(LocalDateTime.class) //addTime f14
                ))).map(new KLineOperators.ToTuple15())
                .returns(KLineOperators.klineTypeInformation());
        //backward
        dayK.filter(new KLineOperators.FilterByRehabType(2))
                .reduce(new Ma5Operators.ClosePriceSum())
                .map(new Ma5Operators.ClosePriceAvg())
                .returns(Ma5Operators.ma5TypeInformation())
                .map(new Ma5Operators.ToRow())
                .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_CN_MA5, Ma5Operators.ma5Types()));
        //forward
        dayK.filter(new KLineOperators.FilterByRehabType(1))
                .reduce(new Ma5Operators.ClosePriceSum())
                .map(new Ma5Operators.ClosePriceAvg())
                .returns(Ma5Operators.ma5TypeInformation())
                .map(new Ma5Operators.ToRow())
                .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_CN_MA5, Ma5Operators.ma5Types()));
        //none
        dayK.filter(new KLineOperators.FilterByRehabType(0))
                .reduce(new Ma5Operators.ClosePriceSum())
                .map(new Ma5Operators.ClosePriceAvg())
                .returns(Ma5Operators.ma5TypeInformation())
                .map(new Ma5Operators.ToRow())
                .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_CN_MA5, Ma5Operators.ma5Types()));
        env.execute(DataStreamJob.class.getName());
    }
}
