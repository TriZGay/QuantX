package io.futakotome.quantx.process;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.quantx.key.RTKLineKey;
import io.futakotome.quantx.pojo.Macd;
import io.futakotome.quantx.pojo.TradeDirection;
import io.futakotome.quantx.pojo.TradeSignal;
import io.futakotome.quantx.utils.Ema;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

public class MacdProcessFunction extends KeyedProcessFunction<RTKLineKey, RTKLMessage, Macd> {
    private final Integer fastPeriod;
    private final Integer slowPeriod;
    private final Integer deaPeriod;

    private ValueState<Double> fastEmaState;
    private ValueState<Double> slowEmaState;
    private ValueState<Double> deaState;
    private ValueState<Double> difState;

    public static final OutputTag<TradeSignal> TRADE_SIGNAL_OUTPUT_TAG = new OutputTag<>("tradeSignal"){};

    public MacdProcessFunction(Integer fastPeriod, Integer slowPeriod, Integer deaPeriod) {
        this.fastPeriod = fastPeriod;
        this.slowPeriod = slowPeriod;
        this.deaPeriod = deaPeriod;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<Double> fastEmaStateDescriptor = new ValueStateDescriptor<>("fastEmaState", Double.class);
        fastEmaState = getRuntimeContext().getState(fastEmaStateDescriptor);
        ValueStateDescriptor<Double> slowEmaStateDescriptor = new ValueStateDescriptor<>("slowEmaState", Double.class);
        slowEmaState = getRuntimeContext().getState(slowEmaStateDescriptor);
        ValueStateDescriptor<Double> deaStateDescriptor = new ValueStateDescriptor<>("deaState", Double.class);
        deaState = getRuntimeContext().getState(deaStateDescriptor);
        ValueStateDescriptor<Double> difStateDescriptor = new ValueStateDescriptor<>("difState", Double.class);
        difState = getRuntimeContext().getState(difStateDescriptor);
    }

    @Override
    public void processElement(RTKLMessage value, KeyedProcessFunction<RTKLineKey, RTKLMessage, Macd>.Context ctx, Collector<Macd> out) throws Exception {
        Double prevFastEma = fastEmaState.value();
        Double prevSlowEma = slowEmaState.value();
        Double prevDeaEma = deaState.value();
        Double prevDif = difState.value();
        Double currentFastEma = Ema.calculate(value.getClosePrice(), prevFastEma, fastPeriod);
        Double currentSlowEma = Ema.calculate(value.getClosePrice(), prevSlowEma, slowPeriod);
        Double currentDif = currentFastEma - currentSlowEma;
        Double currentDeaEma = Ema.calculate(currentDif, prevDeaEma, deaPeriod);
        Double macd = 2 * (currentDif - currentDeaEma);
        if (currentDif > currentDeaEma && prevDif <= prevDeaEma) {
            ctx.output(TRADE_SIGNAL_OUTPUT_TAG, new TradeSignal(ctx.getCurrentKey(), TradeDirection.BUY.getDirection(), value.getClosePrice()));
        } else if (currentDif < currentDeaEma && prevDif >= prevDeaEma) {
            ctx.output(TRADE_SIGNAL_OUTPUT_TAG, new TradeSignal(ctx.getCurrentKey(), TradeDirection.SELL.getDirection(), value.getClosePrice()));
        }
        fastEmaState.update(currentFastEma);
        slowEmaState.update(currentSlowEma);
        deaState.update(currentDeaEma);
        difState.update(currentDif);
        out.collect(new Macd(ctx.getCurrentKey(), currentDif, currentDeaEma, macd));
    }
}
