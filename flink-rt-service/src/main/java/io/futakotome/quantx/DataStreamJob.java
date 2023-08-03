package io.futakotome.quantx;

import io.futakotome.quantx.dto.TradeDateDto;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.FilterOperator;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple15;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.MultipleParameterTool;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.jdbc.JdbcInputFormat;
import org.apache.flink.connector.jdbc.JdbcRowOutputFormat;
import org.apache.flink.connector.jdbc.internal.JdbcOutputFormat;
import org.apache.flink.types.Row;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class DataStreamJob {

    public static void main(String[] args) throws Exception {
        ParameterTool parameter = ParameterTool.fromPropertiesFile(DataStreamJob.class.getResourceAsStream("/base_config.properties"));
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String firstOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).toString();
        String endOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).toString();
        System.out.println("=====大A市场MA数据开始统计=====");
        System.out.println("[" + firstOfYear + "~" + endOfYear + "]");
        List<TradeDateDto> cnMarketTradeDate = env.createInput(JdbcInputFormat.buildJdbcInputFormat()
                .setDrivername(parameter.getRequired("pg.driverName"))
                .setUsername(parameter.getRequired("pg.username"))
                .setPassword(parameter.getRequired("pg.pwd"))
                .setDBUrl(parameter.getRequired("pg.url"))
                .setQuery("select id,market_or_security,time,trade_date_type from t_trade_date where market_or_security = '21,22' and time >= '" + firstOfYear + "'and time <= '" + endOfYear + "' order by time")
                .setRowTypeInfo(new RowTypeInfo(
                        BasicTypeInfo.LONG_TYPE_INFO,
                        BasicTypeInfo.STRING_TYPE_INFO,
                        BasicTypeInfo.STRING_TYPE_INFO,
                        BasicTypeInfo.INT_TYPE_INFO
                )).finish())
                .map((MapFunction<Row, TradeDateDto>) row ->
                        new TradeDateDto(
                                row.getFieldAs(0),
                                row.getFieldAs(1),
                                row.getFieldAs(2),
                                row.getFieldAs(3)
                        ))
                .filter((FilterFunction<TradeDateDto>) tradeDateDto -> {
                    LocalDate time = LocalDate.parse(tradeDateDto.getTime());
                    LocalDate start = LocalDate.parse("2023-07-18");
                    LocalDate end = LocalDate.parse("2023-07-31");
                    return time.isAfter(start) && time.isBefore(end);
                }).first(5)
                .collect();
        if (cnMarketTradeDate.size() < 5) {
            System.err.println("交易日期至少要5天!");
            return;
        }
        TradeDateDto firstTradeDate = cnMarketTradeDate.get(0);
        TradeDateDto endTradeDate = cnMarketTradeDate.get(4);
        MapOperator<Row, Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
                Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> dayK = env.createInput(JdbcInputFormat.buildJdbcInputFormat()
                .setDrivername(parameter.getRequired("ck.driverName"))
                .setUsername(parameter.getRequired("ck.username"))
                .setPassword(parameter.getRequired("ck.pwd"))
                .setDBUrl(parameter.getRequired("ck.url"))
                .setQuery("select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time" +
                        " from t_kl_day_raw as t1" +
                        " all inner join" +
                        " (select update_time ,max(add_time) as latest from t_kl_day_raw group by update_time) as t2 " +
                        " on (t2.update_time = t1.update_time ) and (t2.latest = t1.add_time) and (t1.update_time >= '" + firstTradeDate.getTime() + "') and (t1.update_time <= '" + endTradeDate.getTime() + "')" +
                        " order by t1.update_time")
                .setRowTypeInfo(new RowTypeInfo(
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
                )).finish())
                .map((MapFunction<Row, Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
                        Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>>) row ->
                        Tuple15.of(
                                Byte.toUnsignedInt(row.getFieldAs(0)),
                                row.getFieldAs(1),
                                Byte.toUnsignedInt(row.getFieldAs(2)),
                                row.getFieldAs(3),
                                row.getFieldAs(4),
                                row.getFieldAs(5),
                                row.getFieldAs(6),
                                row.getFieldAs(7),
                                row.getFieldAs(8),
                                row.getFieldAs(9),
                                row.getFieldAs(10),
                                row.getFieldAs(11),
                                row.getFieldAs(12),
                                row.getFieldAs(13),
                                row.getFieldAs(14)
                        )
                ).returns(Types.TUPLE(Types.INT, Types.STRING, Types.INT, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE,
                        Types.LONG, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.LOCAL_DATE_TIME, Types.LOCAL_DATE_TIME));
        FilterOperator<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
                Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> noneRehabType = dayK.filter((FilterFunction<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
                Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>>) tuple15 -> tuple15.f2.equals(0));
//        FilterOperator<KLineDto> forwardRehabType = dayK.filter((FilterFunction<KLineDto>) kLineDto -> kLineDto.getRehabType().equals(1));
//        FilterOperator<KLineDto> backwardRehabType = dayK.filter((FilterFunction<KLineDto>) kLineDto -> kLineDto.getRehabType().equals(2));
        noneRehabType
                .reduce((ReduceFunction<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>>) (v1, v2) -> Tuple15.of(v1.f0, v1.f1, v1.f2, v1.f3, v1.f4, v1.f5, v1.f6 + v2.f6, v1.f7, v1.f8, v1.f9, v1.f10, v1.f11, v1.f12, v2.f13, v1.f14))
                .map((MapFunction<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>,
                        Tuple5<Integer, String, Double, LocalDateTime, LocalDateTime>>) v1 -> Tuple5.of(v1.f0, v1.f1, v1.f6 / 5, v1.f13.plusDays(1), LocalDateTime.now()))
                .returns(Types.TUPLE(Types.INT, Types.STRING, Types.DOUBLE, Types.LOCAL_DATE_TIME, Types.LOCAL_DATE_TIME))
                .print();
//        env.execute(DataStreamJob.class.getName());
    }
}
