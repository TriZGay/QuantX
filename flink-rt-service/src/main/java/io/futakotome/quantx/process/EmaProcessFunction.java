package io.futakotome.quantx.process;

import io.futakotome.common.message.RTKLMessage;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class EmaProcessFunction extends KeyedProcessFunction<Tuple3<Integer, String, Integer>, RTKLMessage, Tuple2<Tuple3<Integer, String, Integer>, Double>> {
    private ValueState<Double> emaState;
    private Double initialEma;
    private final Double multiplier;

    public EmaProcessFunction(Integer period) {
        this.multiplier = 2.0 / (period + 1);
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<Double> descriptor = new ValueStateDescriptor<>("emaState", Double.class);
        emaState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public void processElement(RTKLMessage value, KeyedProcessFunction<Tuple3<Integer, String, Integer>, RTKLMessage, Tuple2<Tuple3<Integer, String, Integer>, Double>>.Context ctx, Collector<Tuple2<Tuple3<Integer, String, Integer>, Double>> out) throws Exception {
        Double currentEma = emaState.value();
        if (currentEma == null) {
            initialEma = value.getClosePrice();
            currentEma = initialEma;
        } else {
            currentEma = (value.getClosePrice() * multiplier) + (currentEma * (1 - multiplier));
        }
        emaState.update(currentEma);
        Tuple3<Integer, String, Integer> key = Tuple3.of(value.getMarket(), value.getCode(), value.getRehabType());
        out.collect(Tuple2.of(key, currentEma));
    }
}
