package io.futakotome.quantx;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.quantx.key.RTKLineKeySelector;
import io.futakotome.quantx.process.EmaProcessFunction;
import io.futakotome.quantx.process.MaProcessFunction;
import io.futakotome.quantx.source.RTKLineSource;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.functions.co.RichCoMapFunction;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;

import static io.futakotome.common.MessageCommon.RT_KL_MIN_1_CONSUMER_GROUP_STREAM;
import static io.futakotome.common.MessageCommon.RT_KL_MIN_1_TOPIC;

public class QuantXMainJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool configs = ParameterTool.fromPropertiesFile(QuantXMainJob.class.getResourceAsStream("/base_config.properties"));

        KafkaSource<RTKLMessage> source = RTKLineSource.from(configs, RT_KL_MIN_1_CONSUMER_GROUP_STREAM, RT_KL_MIN_1_TOPIC);
        DataStream<RTKLMessage> rtklMin1Source = env.fromSource(source, WatermarkStrategy.forMonotonousTimestamps(), "rtk_min1_source")
                .setParallelism(1);
        KeyedStream<RTKLMessage, Tuple3<Integer, String, Integer>> keyedStream = rtklMin1Source.keyBy(new RTKLineKeySelector());
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
//        keyedStream.process(new EmaProcessFunction(5))
//                .print("ema5-stream");
//        keyedStream.process(new EmaProcessFunction(10))
//                .print("ema10-stream");
        DataStream<Tuple2<Tuple3<Integer, String, Integer>, Double>> ema12Stream = keyedStream.process(new EmaProcessFunction(12));
//                .print("ema12-stream");
//        keyedStream.process(new EmaProcessFunction(20))
//                .print("ema20-stream");
        DataStream<Tuple2<Tuple3<Integer, String, Integer>, Double>> ema26Stream = keyedStream.process(new EmaProcessFunction(26));
//                .print("ema26-stream");
//        keyedStream.process(new EmaProcessFunction(60))
//                .print("ema60-stream");
//        keyedStream.process(new EmaProcessFunction(120))
//                .print("ema120-stream");
        env.execute(QuantXMainJob.class.getName());
    }
}
