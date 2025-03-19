package io.futakotome.quantx.process;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.quantx.key.RTKLineKey;
import io.futakotome.quantx.pojo.EmaResult;
import io.futakotome.quantx.utils.Ema;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class EmaProcessFunction extends KeyedProcessFunction<RTKLineKey, RTKLMessage, EmaResult> {
    private ValueState<Double> emaState;
    private final Integer period;

    public EmaProcessFunction(Integer period) {
        this.period = period;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<Double> descriptor = new ValueStateDescriptor<>("emaState", Double.class);
        emaState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public void processElement(RTKLMessage value, KeyedProcessFunction<RTKLineKey, RTKLMessage, EmaResult>.Context ctx, Collector<EmaResult> out) throws Exception {
        Double currentEma = emaState.value();
        currentEma = Ema.calculate(value.getClosePrice(), currentEma, period);
        emaState.update(currentEma);
        out.collect(new EmaResult(ctx.getCurrentKey(), currentEma));
    }
}
