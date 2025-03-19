package io.futakotome.quantx.process;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.quantx.key.RTKLineKey;
import io.futakotome.quantx.pojo.Macd;
import io.futakotome.quantx.utils.Ema;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class MacdProcessFunction extends KeyedProcessFunction<RTKLineKey, RTKLMessage, Macd> {
    private final Integer fastPeriod;
    private final Integer slowPeriod;
    private final Integer deaPeriod;

    private ValueState<Double> fastEmaState;
    private ValueState<Double> slowEmaState;
    private ValueState<Double> deaState;

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
    }

    @Override
    public void processElement(RTKLMessage value, KeyedProcessFunction<RTKLineKey, RTKLMessage, Macd>.Context ctx, Collector<Macd> out) throws Exception {
        Double currentFastEma = fastEmaState.value();
        Double currentSlowEma = slowEmaState.value();
        Double currentDeaEma = deaState.value();
        currentFastEma = Ema.calculate(value.getClosePrice(), currentFastEma, fastPeriod);
        currentSlowEma = Ema.calculate(value.getClosePrice(), currentSlowEma, slowPeriod);
        Double dif = currentFastEma - currentSlowEma;
        currentDeaEma = Ema.calculate(dif, currentDeaEma, deaPeriod);
        Double macd = 2 * (dif - currentDeaEma);
        fastEmaState.update(currentFastEma);
        slowEmaState.update(currentSlowEma);
        deaState.update(currentDeaEma);
        out.collect(new Macd(ctx.getCurrentKey(), dif, currentDeaEma, macd));
    }
}
