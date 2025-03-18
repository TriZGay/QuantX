package io.futakotome.quantx.process;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.quantx.key.RTKLineKey;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

public class MaProcessFunction extends ProcessWindowFunction<RTKLMessage, Tuple2<RTKLineKey, Double>, RTKLineKey, GlobalWindow> {
    private final Integer granularity;

    public MaProcessFunction(Integer granularity) {
        this.granularity = granularity;
    }

    @Override
    public void process(RTKLineKey key, ProcessWindowFunction<RTKLMessage, Tuple2<RTKLineKey, Double>, RTKLineKey, GlobalWindow>.Context context, Iterable<RTKLMessage> elements, Collector<Tuple2<RTKLineKey, Double>> out) throws Exception {
        Double sum = 0.0;
        Double avg;
//        StringBuilder debuggerInfo = new StringBuilder("MA5[");
        for (RTKLMessage element : elements) {
            sum += element.getClosePrice();
//            debuggerInfo.append(element.getClosePrice()).append(" ");
        }
//        debuggerInfo.append("]");
        avg = sum / granularity;
//        debuggerInfo.append("=").append(avg);
//        System.out.println(key + "=>" + debuggerInfo);
        out.collect(Tuple2.of(key, avg));
    }
}