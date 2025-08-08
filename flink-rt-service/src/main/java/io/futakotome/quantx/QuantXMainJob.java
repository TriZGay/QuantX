package io.futakotome.quantx;

import io.futakotome.quantx.domain.Ema;
import io.futakotome.quantx.domain.KLine;
import io.futakotome.quantx.domain.Keys;
import io.futakotome.quantx.source.EmaSource;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;
import java.util.List;

public class QuantXMainJob {
    private static List<KLine.KLineDto> messages() {
        KLine.KLineDto message1 = new KLine.KLineDto();
        message1.setMarket(1);
        message1.setCode("00700");
        message1.setRehabType(0);
        KLine.KLineDto message2 = new KLine.KLineDto();
        message2.setMarket(1);
        message2.setCode("00700");
        message2.setRehabType(1);
        KLine.KLineDto message3 = new KLine.KLineDto();
        message3.setMarket(1);
        message3.setCode("00700");
        message3.setRehabType(2);
        return Arrays.asList(message1, message2, message3);
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool configs = ParameterTool.fromPropertiesFile(QuantXMainJob.class.getResourceAsStream("/base_config.properties"));
        env.fromData(messages());
        //       DataStream<KLine.KLineDto> kStream = env.fromData(messages());
        //        emaSource.join(kStream)
        //                .where(new Ema.CodeRehabTypeKeySelector())
        //                .equalTo(new KLine.CodeRehabTypeKeySelector())

        //        KafkaSource<RTKLMessage> source = RTKLine.fromKafka(configs, RT_KL_MIN_1_CONSUMER_GROUP_STREAM, RT_KL_MIN_1_TOPIC);
        //
        //        DataStream<RTKLMessage> rtklMin1Source = env.fromSource(source, WatermarkStrategy.forMonotonousTimestamps(), "rtk_min1_source")
        //                .setParallelism(2);
        //        KeyedStream<RTKLMessage, RTKLineKey> keyedStream = rtklMin1Source.keyBy(new RTKLineKeySelector());
        //        keyedStream.countWindow(5, -4)
        //                .process(new MaProcessFunction(5))
        //                .print("ma5-stream");
        //        keyedStream.countWindow(10, -9)
        //                .process(new MaProcessFunction(10))
        //                .print("ma10-stream");
        //        keyedStream.countWindow(20, -19)
        //                .process(new MaProcessFunction(20))
        //                .print("ma20-stream");
        //        keyedStream.countWindow(30, -29)
        //                .process(new MaProcessFunction(30))
        //                .print("ma30-stream");
        //        keyedStream.countWindow(60, -59)
        //                .process(new MaProcessFunction(60))
        //                .print("ma60-stream");
        //        keyedStream.countWindow(120, -119)
        //                .process(new MaProcessFunction(120))
        //                .print("ma120-stream");
        //        SingleOutputStreamOperator<Macd> macdStream = keyedStream.process(new MacdProcessFunction(12, 26, 9));
        //        macdStream.getSideOutput(MacdProcessFunction.TRADE_SIGNAL_OUTPUT_TAG)
        //                .print("signal-stream");
        //                .sinkTo()
        //                .setParallelism(2);
        //        macdStream.print("macd-stream");
        //        keyedStream.process(new EmaProcessFunction(5))
        //                .map(new EmaMapFunction())
        //                .sinkTo(RTEmaSink.toKafka(configs, RT_EMA5_TOPIC))
        //                .setParallelism(2);
        //        keyedStream.process(new EmaProcessFunction(10))
        //                .print("ema10-stream");
        //        DataStream<Tuple2<Tuple3<Integer, String, Integer>, Double>> ema12Stream = keyedStream.process(new EmaProcessFunction(12));
        //                .print("ema12-stream");
        //        keyedStream.process(new EmaProcessFunction(20))
        //                .print("ema20-stream");
        //        DataStream<Tuple2<Tuple3<Integer, String, Integer>, Double>> ema26Stream = keyedStream.process(new EmaProcessFunction(26));
        //                .print("ema26-stream");
        //        keyedStream.process(new EmaProcessFunction(60))
        //                .print("ema60-stream");
        //        keyedStream.process(new EmaProcessFunction(120))
        //                .print("ema120-stream");
        //        DataStream<Tuple2<Tuple3<Integer, String, Integer>, Double>> difStream = ema12Stream.join(ema26Stream)
        //                .where(new KeySelector<>() {
        //                    @Override
        //                    public Tuple3<Integer, String, Integer> getKey(Tuple2<Tuple3<Integer, String, Integer>, Double> value) throws Exception {
        //                        return value.f0;
        //                    }
        //                })
        //                .equalTo(new KeySelector<>() {
        //                    @Override
        //                    public Tuple3<Integer, String, Integer> getKey(Tuple2<Tuple3<Integer, String, Integer>, Double> value) throws Exception {
        //                        return value.f0;
        //                    }
        //                })
        //                .window(GlobalWindows.create())
        //                .evictor(CountEvictor.of(9)) //size
        //                .trigger(CountTrigger.of(-8)) //slide
        //                .apply(new JoinFunction<>() {
        //                    @Override
        //                    public Tuple2<Tuple3<Integer, String, Integer>, Double> join(Tuple2<Tuple3<Integer, String, Integer>, Double> first, Tuple2<Tuple3<Integer, String, Integer>, Double> second) throws Exception {
        //                        //求dif
        //                        System.out.println(first.f0 + "->" + second.f0 + "=" + first.f1 + "-" + second.f1);
        //                        return Tuple2.of(first.f0, first.f1 - second.f1);
        //                    }
        //                });
        env.execute(QuantXMainJob.class.getName());
    }
}
