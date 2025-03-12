package io.futakotome.quantx.process;

import io.futakotome.common.message.RTKLMessage;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

public class MaProcessFunction extends ProcessWindowFunction<RTKLMessage, Tuple2<Tuple3<Integer, String, Integer>, Double>, Tuple3<Integer, String, Integer>, GlobalWindow> {
    private final Integer granularity;

    public MaProcessFunction(Integer granularity) {
        this.granularity = granularity;
    }

    @Override
    public void process(Tuple3<Integer, String, Integer> key, ProcessWindowFunction<RTKLMessage, Tuple2<Tuple3<Integer, String, Integer>, Double>, Tuple3<Integer, String, Integer>, GlobalWindow>.Context context, Iterable<RTKLMessage> elements, Collector<Tuple2<Tuple3<Integer, String, Integer>, Double>> out) throws Exception {
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