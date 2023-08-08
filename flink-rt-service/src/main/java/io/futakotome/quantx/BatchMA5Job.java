package io.futakotome.quantx;

import io.futakotome.quantx.dto.TradeDateDto;
import io.futakotome.quantx.fomatters.JdbcFormatter;
import io.futakotome.quantx.operators.KLineOperators;
import io.futakotome.quantx.operators.Ma5Operators;
import io.futakotome.quantx.operators.TradeDateOperators;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple15;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.types.Row;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BatchMA5Job {
    private static final String QUERY_TRADE_DATE_RECENT_5 = "select id,market_or_security,time,trade_date_type from t_trade_date where market_or_security = '%s' and time < '%s' order by time desc limit 5";
    private static final String QUERY_DISTINCT_CODE = "select distinct code from t_kl_day_raw prewhere market = %s";
    private static final String QUERY_DAY_K =
            "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time" +
                    " from t_kl_day_raw as t1" +
                    " all inner join" +
                    " (select update_time ,max(add_time) as latest from t_kl_day_raw prewhere code = '%s' group by update_time) as t2 " +
                    " on (t2.update_time = t1.update_time ) and (t2.latest = t1.add_time) and (t1.update_time >= '%s') and (t1.update_time <= '%s') and (code = '%s')" +
                    " order by t1.update_time";
    private static final String INSERT_MA5 = "insert into t_ma5 values (?,?,?,?,?,?)";
    private static final List<String> MARKETS = Arrays.asList("1", "21,22");

    public static void main(String[] args) throws Exception {
        ParameterTool parameter = ParameterTool.fromPropertiesFile(BatchMA5Job.class.getResourceAsStream("/base_config.properties"));
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        ParameterTool commandLieParameter = ParameterTool.fromArgs(args);
        //默认全量全市场批处理
        boolean isAll = commandLieParameter.getBoolean("all", false);
//        MapOperator<Row, TradeDateDto> tradeDateRowMap = env.createInput(
//                JdbcFormatter.inputFromPg(parameter,
//                        String.format(QUERY_TRADE_DATE, firstOfYear, endOfYear),
//                        new RowTypeInfo(
//                                BasicTypeInfo.LONG_TYPE_INFO,
//                                BasicTypeInfo.STRING_TYPE_INFO,
//                                BasicTypeInfo.STRING_TYPE_INFO,
//                                BasicTypeInfo.INT_TYPE_INFO
//                        )))
//                .map(new TradeDateOperators.ToTradeDatePojo());
        if (isAll) {

        } else {
            //指定市场 默认大A
            String market = commandLieParameter.get("market", "21,22");
            //指定日期 默认今天
            String date = commandLieParameter.get("date", LocalDate.now().toString());
            Set<String> codes = new HashSet<>();
            if (market.contains(",")) {
                //有逗号的是大A
                String[] cnMarkets = market.split(",");
                Arrays.stream(cnMarkets).forEach(cnMarket -> {
                    try {
                        List<String> tmpCodes = env.createInput(
                                JdbcFormatter.inputFromClickhouse(parameter,
                                        String.format(QUERY_DISTINCT_CODE, cnMarket),
                                        new RowTypeInfo(Types.STRING)))
                                .map((MapFunction<Row, String>) row -> row.getFieldAs(0))
                                .collect();
                        codes.addAll(tmpCodes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                List<String> tmpCodes = env.createInput(
                        JdbcFormatter.inputFromClickhouse(parameter,
                                String.format(QUERY_DISTINCT_CODE, market),
                                new RowTypeInfo(Types.STRING))
                ).map((MapFunction<Row, String>) row -> row.getFieldAs(0))
                        .collect();
                codes.addAll(tmpCodes);
            }
            List<TradeDateDto> marketTradeDates = env.createInput(
                    JdbcFormatter.inputFromPg(parameter,
                            String.format(QUERY_TRADE_DATE_RECENT_5, market, date),
                            TradeDateOperators.tradeDateRowTypeInfo()))
                    .map(new TradeDateOperators.ToTradeDatePojo())
                    .collect();
            codes.forEach(code -> {
                if (marketTradeDates != null && marketTradeDates.size() != 5) {
                    System.err.println(BatchMA5Job.class.getName() + ":交易区间长度不足5!停止计算...");
                } else if (marketTradeDates != null) {
                    //交易日倒序查询
                    TradeDateDto start = marketTradeDates.get(4);
                    TradeDateDto end = marketTradeDates.get(0);
                    MapOperator<Row, Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
                            Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> dayK = env.createInput(
                            JdbcFormatter.inputFromClickhouse(
                                    parameter,
                                    String.format(QUERY_DAY_K, code, start.getTime(), end.getTime(), code),
                                    KLineOperators.klineRowTypeInfo()
                            )).map(new KLineOperators.ToTuple15())
                            .returns(KLineOperators.klineTypeInformation());
                    //backward
                    dayK.filter(new KLineOperators.FilterByRehabType(2))
                            .reduce(new Ma5Operators.ClosePriceSum())
                            .map(new Ma5Operators.ClosePriceAvg())
                            .returns(Ma5Operators.ma5TypeInformation())
                            .map(new Ma5Operators.ToRow())
                            .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_MA5, Ma5Operators.ma5Types()));
                    //forward
                    dayK.filter(new KLineOperators.FilterByRehabType(1))
                            .reduce(new Ma5Operators.ClosePriceSum())
                            .map(new Ma5Operators.ClosePriceAvg())
                            .returns(Ma5Operators.ma5TypeInformation())
                            .map(new Ma5Operators.ToRow())
                            .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_MA5, Ma5Operators.ma5Types()));
                    //none
                    dayK.filter(new KLineOperators.FilterByRehabType(0))
                            .reduce(new Ma5Operators.ClosePriceSum())
                            .map(new Ma5Operators.ClosePriceAvg())
                            .returns(Ma5Operators.ma5TypeInformation())
                            .map(new Ma5Operators.ToRow())
                            .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_MA5, Ma5Operators.ma5Types()));
                }
            });
        }

        env.execute(BatchMA5Job.class.getName());
    }
}
