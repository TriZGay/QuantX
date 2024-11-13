package io.futakotome.quantx.jobs;

import io.futakotome.quantx.dto.TradeDateDto;
import io.futakotome.quantx.fomatters.JdbcFormatter;
import io.futakotome.quantx.operators.KLineOperators;
import io.futakotome.quantx.operators.Ma20Operators;
import io.futakotome.quantx.operators.TradeDateOperators;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple15;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.types.Row;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class BatchDayKMA20Job {
    private static final String QUERY_TRADE_DATE_RECENT_20 = "select id,market_or_security,time,trade_date_type from t_trade_date where market_or_security = '%s' and time < '%s' order by time desc limit 20";
    private static final String QUERY_DISTINCT_CODE = "select distinct code from t_kl_day_raw prewhere market = %s";
    private static final String QUERY_DAY_K =
            "select market,code,rehab_type,high_price,open_price,low_price,close_price,last_close_price,volume,turnover,turnover_rate,pe,change_rate,update_time,add_time" +
                    " from t_kl_day_raw as t1" +
                    " all inner join" +
                    " (select update_time ,max(add_time) as latest from t_kl_day_raw prewhere code = '%s' group by update_time) as t2 " +
                    " on (t2.update_time = t1.update_time ) and (t2.latest = t1.add_time) and (t1.update_time >= '%s') and (t1.update_time <= '%s') and (code = '%s')" +
                    " order by t1.update_time";
    private static final String INSERT_MA20 = "insert into t_ma20_day values (?,?,?,?,?,?)";
    private static final List<String> MARKETS = Arrays.asList("1", "21,22");


    private final ExecutionEnvironment env;
    private final ParameterTool configs;
    private final ParameterTool commandLieParameter;

    public BatchDayKMA20Job(ExecutionEnvironment env, ParameterTool configs, String[] args) {
        this.env = env;
        this.configs = configs;
        this.commandLieParameter = ParameterTool.fromArgs(args);
    }

    public void run() throws Exception {
        //指定日期 默认今天
        String date = commandLieParameter.get("date", LocalDate.now().toString());
        //默认全量全市场批处理
        boolean isAll = commandLieParameter.getBoolean("all", true);
        if (isAll) {
            MARKETS.forEach(market ->
                    computeMA20PerMarket(configs, env, date, market));
        } else {
            //指定市场 默认大A
            String market = commandLieParameter.get("market", "21,22");
            computeMA20PerMarket(configs, env, date, market);
        }

        env.execute(BatchDayKMA20Job.class.getName());
    }

    private static void computeMA20PerMarket(ParameterTool parameter, ExecutionEnvironment env, String date, String market) {
        Set<String> codes = new HashSet<>(needComputedCodes(market, env, parameter));
        List<TradeDateDto> marketTradeDates = queryTradeDatesRecent20(market, date, env, parameter);
        codes.forEach(code -> {
            if (marketTradeDates != null && marketTradeDates.size() != 20) {
                System.err.println(BatchDayKMA20Job.class.getName() + ":交易区间长度不足20!停止计算...");
            } else if (marketTradeDates != null) {
                //交易日倒序查询
                TradeDateDto start = marketTradeDates.get(19);
                TradeDateDto end = marketTradeDates.get(0);
                System.out.println("关键SQL:" + String.format(QUERY_DAY_K, code, start.getTime(), end.getTime(), code));
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
                        .reduce(new Ma20Operators.ClosePriceSum())
                        .map(new Ma20Operators.ClosePriceAvg())
                        .returns(Ma20Operators.ma20TypeInformation())
                        .map(new Ma20Operators.ToRow())
                        .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_MA20, Ma20Operators.ma20Types()));
                //forward
                dayK.filter(new KLineOperators.FilterByRehabType(1))
                        .reduce(new Ma20Operators.ClosePriceSum())
                        .map(new Ma20Operators.ClosePriceAvg())
                        .returns(Ma20Operators.ma20TypeInformation())
                        .map(new Ma20Operators.ToRow())
                        .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_MA20, Ma20Operators.ma20Types()));
                //none
                dayK.filter(new KLineOperators.FilterByRehabType(0))
                        .reduce(new Ma20Operators.ClosePriceSum())
                        .map(new Ma20Operators.ClosePriceAvg())
                        .returns(Ma20Operators.ma20TypeInformation())
                        .map(new Ma20Operators.ToRow())
                        .output(JdbcFormatter.outputToClickhouse(parameter, INSERT_MA20, Ma20Operators.ma20Types()));
            }
        });
    }

    private static List<TradeDateDto> queryTradeDatesRecent20(String market, String date, ExecutionEnvironment env, ParameterTool parameter) {
        List<TradeDateDto> tradeDateDtos = new ArrayList<>();
        try {
            tradeDateDtos = env.createInput(
                    JdbcFormatter.inputFromPg(parameter,
                            String.format(QUERY_TRADE_DATE_RECENT_20, market, date),
                            TradeDateOperators.tradeDateRowTypeInfo()))
                    .map(new TradeDateOperators.ToTradeDatePojo())
                    .collect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tradeDateDtos;
    }

    private static List<String> needComputedCodes(String market, ExecutionEnvironment env, ParameterTool parameter) {
        if (market.contains(",")) {
            //有逗号的是大A
            String[] cnMarkets = market.split(",");
            List<String> cnCodes = new ArrayList<>();
            Arrays.stream(cnMarkets).forEach(cnMarket -> {
                try {
                    cnCodes.addAll(
                            env.createInput(
                                    JdbcFormatter.inputFromClickhouse(parameter,
                                            String.format(QUERY_DISTINCT_CODE, cnMarket),
                                            new RowTypeInfo(Types.STRING)))
                                    .map((MapFunction<Row, String>) row -> row.getFieldAs(0))
                                    .collect()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return cnCodes;
        } else {
            List<String> otherMarketCodes = new ArrayList<>();
            try {
                otherMarketCodes.addAll(
                        env.createInput(
                                JdbcFormatter.inputFromClickhouse(parameter,
                                        String.format(QUERY_DISTINCT_CODE, market),
                                        new RowTypeInfo(Types.STRING))
                        ).map((MapFunction<Row, String>) row -> row.getFieldAs(0))
                                .collect()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return otherMarketCodes;
        }
    }
}
